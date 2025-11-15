import { Component, EventEmitter, Input, Output, OnChanges} from '@angular/core';
import { Actividad } from '../../models/actividad.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SharedModule } from '../../../../core/shared/shared-module';

@Component({
  selector: 'app-form-actividad',
  imports: [
    SharedModule
  ],
  templateUrl: './form-actividad.html',
  styleUrl: './form-actividad.css'
})
export class FormActividad implements OnChanges {

  @Input() actividadData?: Actividad; //datos para editar
  @Output() formSubmit = new EventEmitter<any>();
  
  @Input() actividadForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.actividadForm = this.fb.group({
      nombre: ['', Validators.required],
      fecha: ['', Validators.required],
      responsable: ['', Validators.required],
      lugar: ['', Validators.required],
      asignado: [0, [Validators.required, Validators.min(0)]],
      ejecutado: [0, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnChanges() {
    if(this.actividadData) {
      this.actividadForm.patchValue(this.actividadData);
    }
  }

  onSubmit() {
    if (this.actividadForm.valid) {
      this.formSubmit.emit(this.actividadForm.value);
    }
  }

 
}
