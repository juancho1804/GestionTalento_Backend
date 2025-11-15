import { Component } from '@angular/core';
import { SharedModule } from '../../../core/shared/shared-module';
import { ActivatedRoute, Router } from '@angular/router';
import { PlanesService } from '../../dashboard/planes.service';
import { Plan } from '../../dashboard/models/plan.model';
import { Actividad } from '../models/actividad.model';
import { ActivitiesService } from '../activities/activities.service';

@Component({
  selector: 'app-details',
  imports: [SharedModule],
  templateUrl: './details.html',
  styleUrl: './details.css'
})
export class Details {

  planNombre='';
  resumenPlan: Partial<Plan> = {};
  actividades: any[] = [];
  public displayedColumns: string[] = ['nombre','fecha','responsable','asignado','ejecutado','estado','acciones'];

  constructor(
    private route: ActivatedRoute,
    private planesService: PlanesService,
    private router: Router,
    private actividadesService: ActivitiesService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');;
    //cargar actividades para que behavior subject funcione 
    this.actividadesService.loadActividadesByPlan(id!);
    //Suscribirse al observable de actividades actualiciones automaticas
    this.actividadesService.actividades$.subscribe(data => {
      this.actividades = data;
    });
    //obtener el plan por id
    this.planesService.getPlanById(id!).subscribe(plan => {
      if (plan) {
        this.planNombre = plan.name;
        this.resumenPlan = {
          presupuestoAsignado: plan.presupuestoAsignado,
          presupuestoEjecutado: 0, // puedes hacer lógica para sumar actividades ejecutadas
          presupuestoDisponible: plan.presupuestoDisponible,
          actividadesTotales: plan.actividadesTotales
        };
        this.actividadesService.getActividadesByPlan(String(plan.id)).subscribe(acts => {
          this.actividades = acts;
        })
      } else {
        this.planNombre = 'Plan no encontrado';
        this.resumenPlan = {};
        this.actividades = [];
      }
    });
  }

  volver() {
    this.router.navigate(['/admin-dashboard']);
  }

  formatCurrency(value: number): string {
    return '$ ' + value.toLocaleString('es-CO');
  }

 crearActividad() {
  const planId = this.route.snapshot.paramMap.get('id');
    if (!planId || !this.planNombre) return; // check de seguridad

    let ruta = '';

    switch (this.planNombre.trim().toLowerCase()) {
      case 'plan de capacitación':
        ruta = 'crear-capacitacion';
        break;
      case 'plan de incentivos':
        ruta = 'crear-incentivos';
        break;
      case 'plan de bienestar':
        ruta = 'crear-bienestar';
        break;
      default:
        console.warn('Plan desconocido:', this.planNombre);
        return;
    }

    this.router.navigate(['/planes', planId, ruta]);
  }


  getEstadoActividad(actividad: Actividad): string {
    const hoy = new Date();
    const fechaActividad = new Date(actividad.fecha);
    if (actividad.estado === 'Pendiente' && fechaActividad < hoy) {
      return 'No realizada';
    }
    return actividad.estado;  
  }

  eliminarActividad(actividad: Actividad) {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return; //check de seguridad
    this.actividadesService.deleteActividad(String(actividad.id)).subscribe(()=> {
      //Recalcula despues de borrar
      this.actividadesService.getActividadesByPlan(id).subscribe(actividades => {
        const sumaAsignado = actividades.reduce((acc, act)=> acc + act.asignado,0);
        this.planesService.getPlanById(id).subscribe(plan => {
          this.planesService.updatePlan({
            ...plan,
            presupuestoDisponible: plan.presupuestoAsignado - sumaAsignado,
            actividadesTotales: actividades.length
          }).subscribe(()=> {
            //Mostrar un mensaje de exito o actualizar la vista si es necesario
            this.actividadesService.loadActividadesByPlan(id); // recarga la lista
            this.planesService.getPlanById(id).subscribe(updatedPlan => {
              this.resumenPlan = {
                presupuestoAsignado: updatedPlan.presupuestoAsignado,
                presupuestoEjecutado: 0, // puedes hacer lógica para sumar actividades ejecutadas
                presupuestoDisponible: updatedPlan.presupuestoDisponible,
                actividadesTotales: updatedPlan.actividadesTotales
              };
            });
          });
        });
      }); 
    });
  }

  verActividad(actividad: Actividad) {
    // La ruta debe apuntar al componente correcto. Ejemplo:
    this.router.navigate([
    '/planes', 
    actividad.planId, 
    'detalles-actividad', 
    actividad.id
    ]);
  }
}
