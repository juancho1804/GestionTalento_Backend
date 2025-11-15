import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedModule } from '../../../core/shared/shared-module';
import { EncargadaDashboardService } from './services/encargada-dashboard.service';
import { Plan } from '../models/plan.model';

interface Indicador {
  titulo: string;
  valor: number;
  colorClass: string;
  icon: string;
}

@Component({
  selector: 'app-encargada-dashboard',
  standalone: true,
  imports: [CommonModule, SharedModule],
  templateUrl: './encargada-dashboard.html',
  styleUrl: './encargada-dashboard.css',
})
export class EncargadaDashboard {
  cargando = true;
  planId!: string;
  planTipo!: string
  datosPlan: Plan | null = null;

  indicadores: Indicador[] = [];
  ejecucionPercent = 0;
  actividadesTotales = 0;
  actividadesCompletadas = 0;
  actividadesPendientes = 0;
  ultimasActividades: { nombre: string; fecha: string; monto: number; estado: string }[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dashboardService: EncargadaDashboardService
  ) {}

  ngOnInit(): void {
    this.planTipo = this.route.snapshot.paramMap.get('planTipo')!;
    this.planId = this.route.snapshot.paramMap.get('planId')!;
    // valores de ruta ya inicializados
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.dashboardService.getResumenPlan(this.planId).subscribe({
      next: (data) => {
  this.datosPlan = data;
  this.procesarDatos();
  this.cargando = false;
      },
      error: (err) => {
        console.error('❌ Error al cargar datos del plan:', err);
        this.cargando = false;
      },
    });
  }

  procesarDatos(): void {
    if (!this.datosPlan) {
      return;
    }
    const plan = this.datosPlan;
    const actividades = plan.actividades || [];

    this.actividadesTotales = actividades.length;
    this.actividadesCompletadas = actividades.filter(
      (a: any) => a.estado === 'Completada'
    ).length;
    this.actividadesPendientes = this.actividadesTotales - this.actividadesCompletadas;

    const ejecutado = plan.presupuestoAsignado - plan.presupuestoDisponible;
    this.ejecucionPercent = plan.presupuestoAsignado
      ? Math.round((ejecutado / plan.presupuestoAsignado) * 100)
      : 0;

    this.indicadores = [
      {
        titulo: 'Presupuesto Asignado',
        valor: plan.presupuestoAsignado,
        icon: 'attach_money',
        colorClass: 'blue',
      },
      {
        titulo: 'Presupuesto Ejecutado',
        valor: ejecutado,
        icon: 'trending_up',
        colorClass: 'purple',
      },
      {
        titulo: 'Presupuesto Disponible',
        valor: plan.presupuestoDisponible,
        icon: 'account_balance_wallet',
        colorClass: 'green',
      },
      {
        titulo: 'Número de Actividades',
        valor: this.actividadesTotales,
        icon: 'event_note',
        colorClass: 'orange',
      },
    ];

    this.ultimasActividades = [...actividades]
      .sort((a, b) => new Date(b.fecha).getTime() - new Date(a.fecha).getTime())
      .slice(0, 3)
      .map((a) => ({
        nombre: a.nombre,
        fecha: new Date(a.fecha).toLocaleDateString(),
        monto: a.asignado || 0,
        estado: a.estado,
      }));
  }

  onNavigate(opcion: string): void {
    
    const rutas: Record<string, string> = {
      Actividades: `/encargada/planes/${this.planTipo}/${this.planId}/actividades`,
      Reportes: `/reportes/${this.planId}`,
    };
    this.router.navigate([rutas[opcion]]);
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 0,
    }).format(value);
  }
}
