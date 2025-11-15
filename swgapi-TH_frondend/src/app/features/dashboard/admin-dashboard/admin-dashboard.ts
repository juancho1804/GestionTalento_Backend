import { Component, OnInit, ViewChild } from '@angular/core';
import { SharedModule } from '../../../core/shared/shared-module';
import { RouterModule, Router } from '@angular/router';
import { NgApexchartsModule } from 'ng-apexcharts';

import { ModalConfigPresupuesto } from './modal-config-presupuesto/modal-config-presupuesto';

import {
  ChartComponent,
  ApexChart,
  ApexTooltip,
  ApexLegend,
  ApexResponsive
} from 'ng-apexcharts';

import { Plan } from '../models/plan.model';
import { PlanesService } from '../planes.service';
import { MatDialog } from '@angular/material/dialog';
import { HttpClientModule } from '@angular/common/http';

export type ChartSeries = number[];

export type ChartOptions = {
  series: ChartSeries;
  chart: ApexChart & {labels?: string[]};
  responsive: ApexResponsive[];
  legend: ApexLegend;
  tooltip: ApexTooltip;
}

@Component({
  selector: 'app-admin-dashboard',
  imports: [
    SharedModule, 
    NgApexchartsModule, 
    RouterModule,
    HttpClientModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css'
})
export class AdminDashboard implements OnInit {
  @ViewChild('chart') chart!: ChartComponent;

  planes: Plan[] = [];

  totalPresupuesto: number=0;
  totalEjecutado: number=0;
  totalDisponible: number=0;

  totalAnual=0;
  ejecutado=0;
  disponible=0;
  
  public chartOptions: ChartOptions;

  PlanSeleccionado: Plan | null = null;
  monstrarDetalle=false;

    constructor(
    private planesService: PlanesService,
    private dialog: MatDialog,
    private router: Router
  ) {
    this.chartOptions = {
      series: [this.ejecutado, this.disponible],
      chart: {
        type: 'pie',
        width: '100%',
        labels: ['Ejecutado', 'Disponible']
      },
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            }
          }
        }
      ],
      legend: {
        position: 'bottom'
      },
      tooltip: {
        y: {
          formatter: (val) =>
            `$${val.toLocaleString('en-US', { minimumFractionDigits: 0 })}`
        }
      }
    };
  }

  ngOnInit(): void {
      this.planesService.getPlanes().subscribe((data)=>{
        this.planes = data;

        //calcular totales sumando datos de planes
        this.totalPresupuesto = this.planes.reduce(
          (sum, p) => sum + p.presupuestoAsignado, 0
        );
        this.totalDisponible = this.planes.reduce(
          (sum, p) => sum + p.presupuestoDisponible, 0
        );
        this.totalEjecutado = this.totalPresupuesto - this.totalDisponible;

        this.totalAnual = this.totalPresupuesto;
        this.ejecutado = this.totalEjecutado;
        this.disponible = this.totalDisponible;

        //Actualizar grafico
        this.chartOptions = {
          ...this.chartOptions,
          series:[this.ejecutado, this.disponible]
        };
      });
  }

  get porcebtajeEjecutado(): number {
    return Math.round((this.ejecutado / this.totalAnual) * 100);
  }

  abrirModalConfig(): void {
    const dialogRef = this.dialog.open(ModalConfigPresupuesto, {
      width: '600px',
      data: {
        saldoGlobal: this.totalPresupuesto,
        planes: this.planes.map(plan => ({
          id:plan.id,
          name: plan.name,
          presupuesto: plan.presupuestoAsignado ?? 0
        }))
      }
    });

    dialogRef.afterClosed().subscribe((result)=> {
      if (result) {
        result.planes.forEach((planActualizado: { id: number; presupuesto: number })=> {
          const original = this.planes.find(p => p.id === planActualizado.id);
          if(original) {
            const actualizado: Plan = {
              ...original,
              presupuestoAsignado: planActualizado.presupuesto,
              presupuestoDisponible: planActualizado.presupuesto,
              // actividadesTotales: 
            };
            //aAqui llamamos el serviciooo
            this.planesService.updatePlan(actualizado).subscribe();
          }
        });
        //Recargar datos
        setTimeout(() =>  {
          this.planesService.getPlanes().subscribe(data => {
            this.planes = data;
            this.totalPresupuesto = result.saldoGlobal;
            this.totalDisponible = this.planes.reduce((sum, p) => sum + p.presupuestoDisponible, 0);
            this.totalEjecutado = 0;
            this.chartOptions = {
              ...this.chartOptions,
              series: [this.totalEjecutado, this.totalDisponible]
            };
          });
        }, 400); // tiempo para que se actualice el backend (json-server)
      }
    });
  }
  


  detalle(plan: Plan): void {
    this.router.navigate(['/planes', plan.id]);
  }

  informes(plan: Plan): void {
    // Navegar a la sección de informes (por implementar)
  }

}
