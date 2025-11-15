import { Component, EventEmitter, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { SharedModule } from '../../../../../core/shared/shared-module';
import { FormActividad } from '../../base/form-actividad';
import { Actividad } from '../../../models/actividad.model';
import { ActivitiesService } from '../../activities.service';
import { PlanesService } from '../../../../dashboard/planes.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-crear-actividad',
  imports: [SharedModule, FormActividad],
  templateUrl: './crear-actividad.html',
  styleUrl: './crear-actividad.css'
})
export class CrearActividadCapacitacion {
  @Output() formSubmit = new EventEmitter<Actividad>();

  planId: string;
  form: FormGroup;
  isEncargada = false;
  planTipo = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private actividadesService: ActivitiesService,  // nombre consistente
    private planesService: PlanesService,
    private fb: FormBuilder,
    private location: Location
  ) {
    this.planId = this.route.snapshot.paramMap.get('planId')!;
    this.planTipo = this.route.snapshot.paramMap.get('planTipo') || '';
    this.isEncargada = !!this.planTipo;

  // Valores de ruta ya inicializados en el constructor

    // Inicializa tu formulario base
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      fecha: ['', Validators.required],
      responsable: ['', Validators.required],
      lugar: ['', Validators.required],
      asignado: [0, [Validators.required, Validators.min(0)]],
      ejecutado: [0, [Validators.required, Validators.min(0)]],
      // Campos extra SOLO para capacitación
      pagoOrientador: [0],
      pagoTiquetes: [0],
      pagoRefrigerios: [0],
      inscripcion: [0],
      encuestaSatisfaccion: [''],
      informe: ['']
    });
  }

  handleFormSubmit(event: unknown) {
    const actividad = event as Actividad; // Casteo explícito
    this.onFormSubmit(actividad);
  }

  onFormSubmit(actividad: Actividad) {

    actividad.planId = this.planId;
    actividad.estado = 'Pendiente'; // Sobrescribe cualquier valor del formulario

    this.actividadesService.createActividad(this.planId, actividad).subscribe(() => {
      this.planesService.getPlanById(this.planId).subscribe(plan => {
        const nuevoDisponible = plan.presupuestoDisponible - actividad.asignado;
        const nuevoTotales = (plan.actividadesTotales || 0) + 1;
        this.planesService.updatePlan({
          ...plan,
          presupuestoDisponible: nuevoDisponible,
          actividadesTotales: nuevoTotales
        }).subscribe(() => {
          if (this.isEncargada) {
            this.router.navigate(['/encargada/planes', this.planTipo, this.planId, 'actividades']);
          } else {
            this.router.navigate(['/planes', this.planId]);
          }
        });
      });
    });
  }

  guardarActividad() {
    const actividad = {
      ...this.form.value,
      planID: this.planId,
      estado: 'Pendiente' // Sobrescribe cualquier valor del formulario
    };
    // Llama al servicio para guardar
    this.actividadesService.createActividad(this.planId, actividad).subscribe(() => {
      // lógica tras guardar, como volver a la lista
    });
  }

  volver() {
    this.location.back()
  }
}
