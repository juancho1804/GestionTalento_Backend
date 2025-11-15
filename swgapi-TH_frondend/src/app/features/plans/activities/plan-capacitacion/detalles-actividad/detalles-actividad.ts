import { Component, OnInit } from '@angular/core';
import { SharedModule } from '../../../../../core/shared/shared-module';
import { Actividad, ActividadCapacitacion } from '../../../models/actividad.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivitiesService } from '../../activities.service';
import { PlanesService } from '../../../../dashboard/planes.service';
import { Plan } from '../../../../dashboard/models/plan.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { ParticipantsService } from '../../../../participants/participants.service';
import { Participante } from '../../../../participants/models/participants.model';

@Component({
  selector: 'app-detalles-actividad',
  imports: [SharedModule,],
  templateUrl: './detalles-actividad.html',
  styleUrl: './detalles-actividad.css'
})
export class DetallesActividadCapacitacion implements OnInit {


  actividadForm!: FormGroup;
  editMode$ = new BehaviorSubject<boolean>(false);

  detallesId!: string;
  planId!: string;

  actividadData: ActividadCapacitacion = {} as ActividadCapacitacion;
  planData: Plan | null = null;
  fechaActividad: string = '';
  tipoPlan: string = '';
  estadoActividad: string = '';

  participantes: Participante[] = []; // Tipado usando el modelo de participantes
  displayedColumns: string[] = ['nombre', 'cedula', 'dependencia', 'cargo', 'asistencia', 'observaciones'];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private activitiesService: ActivitiesService,
    private planesService: PlanesService,
    private router: Router,
    private participantsService: ParticipantsService
  ) { }

  ngOnInit(): void {
    this.actividadForm = this.fb.group({
      nombre: [{ value: '', disabled: true }, Validators.required],
      fecha: [{ value: '', disabled: true }],
      responsable: [{ value: '', disabled: true }],
      lugar: [{ value: '', disabled: true }],
      asignado: [{ value: '', disabled: true }],
      ejecutado: [{ value: '', disabled: true }],
      disponible: [{ value: '', disabled: true }],
      estado: [{ value: '', disabled: true }],

      pagoOrientador: [{ value: '', disabled: true }],
      pagoTiquetes: [{ value: '', disabled: true }],
      pagoRefrigerios: [{ value: '', disabled: true }],
      asistentesEsperados: [{ value: '', disabled: true }],
      asistentesReales: [{ value: '', disabled: true }],
    });

    this.planId = this.route.snapshot.paramMap.get('planId')!;
    this.detallesId = this.route.snapshot.paramMap.get('detallesId')!;

    this.activitiesService.getActividad(this.detallesId).subscribe({
      next: (actividad) => {
        this.actividadData = actividad as ActividadCapacitacion;
        this.estadoActividad = this.actividadData.estado;
        this.fechaActividad = this.actividadData.fecha
          ? new Date(this.actividadData.fecha).toISOString().split('T')[0] : '';

        this.actividadForm.patchValue({
          nombre: actividad.nombre,
          responsable: actividad.responsable,
          estado: actividad.estado,
          fecha: this.fechaActividad,
          lugar: actividad.lugar,
          asignado: actividad.asignado,
          ejecutado: actividad.ejecutado,
          disponible: Number(actividad.asignado) - Number(actividad.ejecutado),
          pagoOrientador: this.actividadData.pagoOrientador,
          pagoTiquetes: this.actividadData.pagoTiquetes,
          pagoRefrigerios: this.actividadData.pagoRefrigerios,

        });

  // Actividad cargada en memoria

        this.planesService.getPlanById(this.planId).subscribe({
          next: (plan) => {
            this.planData = plan;
            this.tipoPlan = this.planData.name;
            this.fechaActividad = this.actividadData.fecha;
          }
        });
      },

    });

    this.participantsService.getParticipantesByActividad(this.detallesId).subscribe({
      next: (participantes) => {
        this.participantes = participantes;
      }
    });
    this.actividadForm.valueChanges.subscribe(val => {
      // Solo recalcular si está en modo edición
      if (this.editMode$.value) {
        const pagoOrientador = Number(val.pagoOrientador) || 0;
        const pagoTiquetes = Number(val.pagoTiquetes) || 0;
        const pagoRefrigerios = Number(val.pagoRefrigerios) || 0;

        const ejecutado = pagoOrientador + pagoTiquetes + pagoRefrigerios;
        const disponible = (this.actividadData.asignado || 0) - ejecutado;

        // Actualiza ambos valores sin emitir otro evento (para evitar loops)
        this.actividadForm.patchValue(
          { ejecutado, disponible },
          { emitEvent: false }
        );
      }
    });
  }

  // Acción para cambiar asistencia en la tabla
  toggleAsistencia(p: Participante) {
    p.asistio = !p.asistio;
    this.participantsService.updateParticipante(p).subscribe();
  }

  volver(): void {
    // Navega de regreso al plan o como desees
    this.router.navigate(['/planes', this.planId]);
  }

  toggleEdit(): void {
    const isEditing = this.editMode$.value;

    if (isEditing) {
      // Guardar solo los campos relacionados con capacitación
      const camposCapacitacion = [
        'pagoOrientador',
        'pagoTiquetes',
        'pagoRefrigerios',
        'asistentesEsperados',
        'asistentesReales'
      ];

      // Obtenemos los valores actuales del formulario, pero solo de esos campos
      const valoresActualizados = camposCapacitacion.reduce((obj, key) => {
        (obj as Record<string, unknown>)[key] = this.actividadForm.get(key)?.value;
        return obj;
      }, {} as Record<string, unknown>);

  const pagoOrientador = Number((valoresActualizados['pagoOrientador'] as any)) || 0;
  const pagoTiquetes = Number((valoresActualizados['pagoTiquetes'] as any)) || 0;
  const pagoRefrigerios = Number((valoresActualizados['pagoRefrigerios'] as any)) || 0;

      const ejecutado = pagoOrientador + pagoTiquetes + pagoRefrigerios;
      const disponible = (this.actividadData.asignado || 0) - ejecutado;

      // Fusionamos con la actividad actual
      const updateActividad: ActividadCapacitacion = {
        ...this.actividadData,
        ...(valoresActualizados as Partial<ActividadCapacitacion>),
        pagoOrientador,
        pagoTiquetes,
        pagoRefrigerios,
        ejecutado,
      };
      // El presupuesto disponible se recalcula automáticamente
      this.actividadForm.patchValue({ ejecutado, disponible });

      this.activitiesService.updateActividad(updateActividad).subscribe({
        next: (resp) => {
          // Actualización aplicada correctamente
          this.actividadData = updateActividad;
          this.actividadForm.disable();
          this.editMode$.next(false);
        },
      });
    } else {
      // Activar edición solo de los campos de capacitación
      this.editMode$.next(true);
      [
        'pagoOrientador',
        'pagoTiquetes',
        'pagoRefrigerios',
        'asistentesEsperados',
        'asistentesReales',
      ].forEach((field) => this.actividadForm.get(field)?.enable());
    }
  }

  agregarParticipante() {
    const nuevo: Participante = {
      actividadId: this.detallesId,
      nombre: 'Participante simulado',
      cedula: '12345678',
      dependencia: 'Tal dependencia',
      cargo: 'Asistente',
      asistio: false
    };
    this.participantsService.addParticipante(nuevo).subscribe();
  }

  get totalInscritos(): number {
    return this.participantes ? this.participantes.length : 0;
  }

  get totalAsistieron(): number {
    return this.participantes ? this.participantes.filter(p => p.asistio).length : 0;
  }

}
