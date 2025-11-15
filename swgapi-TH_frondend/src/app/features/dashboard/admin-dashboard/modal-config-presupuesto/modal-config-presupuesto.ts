import { Component, Inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormArray, FormGroup, Validators } from '@angular/forms';
import { SharedModule } from '../../../../core/shared/shared-module';

export interface PlanPresupuestoDTO { id: number; name: string; presupuesto: number }
export interface ModalConfigData { saldoGlobal: number; planes: PlanPresupuestoDTO[] }

@Component({
  selector: 'app-modal-config-presupuesto',
  imports: [ReactiveFormsModule, SharedModule],
  templateUrl: './modal-config-presupuesto.html',
  styleUrl: './modal-config-presupuesto.css'
})
export class ModalConfigPresupuesto {
  form: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<ModalConfigPresupuesto>,
    @Inject(MAT_DIALOG_DATA) public data: ModalConfigData,
    private fb: FormBuilder,
  ) {
    this.form = this.fb.group({
      saldoGlobal: [data?.saldoGlobal ?? 100000000, [Validators.required, Validators.min(0)]],
      planes: this.fb.array([])
    });

    // Inicializar planes si se pasan o valores por defecto
    const planesData = data?.planes ?? [
      { id: 1, name: "Plan de Capacitación", presupuesto: 0 },
      { id: 2, name: "Plan de Bienestar", presupuesto: 0 },
      { id: 3, name: "Plan de Incentivos", presupuesto: 0 },
    ];
    const planesForms = planesData.map((p: { id: number; name: string; presupuesto: number }) =>
    this.fb.group({
    id: p.id,
    name: p.name,
    presupuesto: [p.presupuesto, [Validators.required, Validators.min(0)]]
  })
);
    this.planes.clear();
    planesForms.forEach((ctl: FormGroup) => this.planes.push(ctl));
  }

  get planes(): FormArray {
    return this.form.get('planes') as FormArray;
  }

  get totalAsignado(): number {
    return this.planes.controls.reduce((acc, ctrl) => acc + (ctrl.get('presupuesto')?.value || 0), 0);
  }

  get excedeSaldo(): boolean {
    return this.totalAsignado > this.form.get('saldoGlobal')?.value;
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', minimumFractionDigits: 0 }).format(value);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.excedeSaldo) {
      return;
    }
    this.dialogRef.close(this.form.value);
  }

get planesFormGroups(): FormGroup[] {
  return this.planes.controls as FormGroup[];
}

onSaldoGlobalChange(valor: string) {
  const limpio = valor.replace(/\D/g,'');
  const num = parseInt(limpio, 10) || 0;
  this.form.get('saldoGlobal')?.setValue(num);
}


onPlanInputChange(index: number, valor: string) {
  const limpio = valor.replace(/[^\d]/g, '');
  const num = parseInt(limpio, 10) || 0;
  this.planesFormGroups[index].get('presupuesto')?.setValue(num);
}


}
