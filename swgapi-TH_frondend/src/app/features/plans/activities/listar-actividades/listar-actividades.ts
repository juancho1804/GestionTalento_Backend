import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../../../core/shared/shared-module';
import { ActivitiesService } from '../activities.service'; // importa tu servicio
import { Actividad } from '../../models/actividad.model'; 

@Component({
  selector: 'app-listar-actividades',
  standalone: true,
  imports: [CommonModule,FormsModule, SharedModule],
  templateUrl: './listar-actividades.html',
  styleUrls: ['./listar-actividades.css']
})
export class ListarActividades implements OnInit {
  planId!: string;
  actividades: Actividad[] = [];
  actividadesFiltradas: Actividad[] = [];
  cargando = true;
  filtro = '';
  planTipo!: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private actividadesService: ActivitiesService
  ) {}

 ngOnInit() {
    this.planTipo = this.route.snapshot.paramMap.get('planTipo') || '';
    this.planId = this.route.snapshot.paramMap.get('planId')!;
    
    
    // Suscribirse al observable del servicio — refleja en tiempo real los cambios del BehaviorSubject
    this.actividadesService.actividades$.subscribe({
      next: (data) => {
        this.actividades = data;
        this.actividadesFiltradas = data;
        this.cargando = false;
      },
      error: () => this.cargando = false
    });
    // Cargar datos iniciales
    this.actividadesService.loadActividadesByPlan(this.planId);
  }

  eliminar(id: string): void {
    if (!id) {
      console.error('ID inválido para eliminar actividad');
      return;
    }
    if (confirm('¿Deseas eliminar esta actividad?')) {
      this.actividadesService.deleteActividad(id).subscribe({
        next: () => {
          // Recargar lista inmediatamente después de eliminar
          this.actividadesService.loadActividadesByPlan(this.planId);
        },
        error: (err) => console.error('Error al eliminar actividad', err)
      });
    }
  }

  filtrar(): void {
    const f = this.filtro.toLowerCase();
    this.actividadesFiltradas = this.actividades.filter(
      (a) =>
        a.nombre.toLowerCase().includes(f) ||
        a.responsable.toLowerCase().includes(f)
    );
  }

  crearActividad(): void {
    switch (this.planTipo) {
      case 'bienestar':
        this.router.navigate([`/encargada/planes/${this.planTipo}/${this.planId}/crear-bienestar`]);
        break;
      case 'capacitacion':
        this.router.navigate([`/encargada/planes/${this.planTipo}/${this.planId}/crear-capacitacion`]);
        break;
      case 'incentivos':
        this.router.navigate([`/encargada/planes/${this.planTipo}/${this.planId}/crear-incentivos`]);
        break;
    }
  }


  verDetalles(id: string) {
    if (!id) return;
    switch (this.planTipo) {
      case 'bienestar':
        this.router.navigate([`/planes/${this.planId}/detalles-actividad/${id}`], {
          queryParams: { tipo: 'bienestar' }
        });
        break;
      case 'capacitacion':
        this.router.navigate([`/planes/${this.planId}/detalles-actividad/${id}/${this.planTipo}`], {
          queryParams: { tipo: 'capacitacion' }
        });
        break;
      case 'incentivos':
        this.router.navigate([`/planes/${this.planId}/detalles-actividad/${id}/${this.planTipo}`], {
          queryParams: { tipo: 'incentivos' }
        });
        break;
    }
  }


  editar(id: string): void {
    if (!id) return;
    this.router.navigate([`/encargada/planes/${this.planId}/editar-actividad/${id}`]);
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 0
    }).format(value);
  }

  
}
