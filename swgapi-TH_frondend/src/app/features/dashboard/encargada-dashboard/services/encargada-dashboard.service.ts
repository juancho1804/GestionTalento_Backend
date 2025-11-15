import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forkJoin, map, Observable } from 'rxjs';
import { Plan } from '../../models/plan.model';
import { Actividad } from '../../../plans/models/actividad.model';

@Injectable({
  providedIn: 'root'
})
export class EncargadaDashboardService {
   private baseUrl = 'http://localhost:3000'; // JSON Server

  constructor(private http: HttpClient) {}

  // Obtener un plan específico
  getPlanById(planId: string): Observable<Plan> {
    return this.http.get<Plan[]>(`${this.baseUrl}/planes?id=${planId}`).pipe(
      map((planes) => planes[0]) // toma el primer resultado del array
    );
  }

  // Obtener las actividades de ese plan
  getActividadesByPlan(planId: string): Observable<Actividad[]> {
    return this.http.get<Actividad[]>(`${this.baseUrl}/actividades?planId=${planId}`);
  }

  // Obtener resumen combinado
  getResumenPlan(planId: string): Observable<Plan & { actividades: Actividad[]; actividadesCompletadas: number; actividadesPendientes: number }> {
    return forkJoin({
      plan: this.getPlanById(planId),
      actividades: this.getActividadesByPlan(planId)
    }).pipe(
      map(({ plan, actividades }) => {
        return {
          ...plan,
          actividades,
          actividadesCompletadas: actividades.filter(a => a.estado === 'Completada').length,
          actividadesPendientes: actividades.filter(a => a.estado !== 'Completada').length
        };
      })
    );
  }
}
