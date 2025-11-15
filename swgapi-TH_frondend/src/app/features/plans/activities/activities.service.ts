import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Actividad } from '../models/actividad.model';

@Injectable({
  providedIn: 'root'
})
export class ActivitiesService {
  private baseUrl ='http://localhost:3000/actividades'; // Cambia esto por la URL real de tu API

  private actividadesSubject = new BehaviorSubject<Actividad[]>([]);
  actividades$ = this.actividadesSubject.asObservable();

  constructor(private http: HttpClient) { }

  // Cargar actividades y actualizar el BehaviorSubject
  loadActividadesByPlan(planId: string): void {
    this.http.get<Actividad[]>(`${this.baseUrl}?planId=${planId}`)
      .subscribe(actividades => this.actividadesSubject.next(actividades));
  }
  
  //obtener actividades por plan
  getActividadesByPlan(planId: string): Observable<Actividad[]> {
    return this.http.get<Actividad[]>(`${this.baseUrl}?planId=${planId}`);
  }

  //Obtener actividad por id 
  getActividad(id: string): Observable<Actividad> {
    return this.http.get<Actividad>(`${this.baseUrl}/${id}`);
  }

   // Crear una nueva actividad
  createActividad(planId: string, actividad: Actividad): Observable<Actividad> {
    return this.http.post<Actividad>(this.baseUrl, actividad).pipe(
      tap(() => this.loadActividadesByPlan(planId)) // recarga lista para todos los suscriptores
    );
  }

  // Actualizar actividad existente
  updateActividad(actividad: Actividad): Observable<Actividad> {
    return this.http.put<Actividad>(`${this.baseUrl}/${actividad.id}`, actividad).pipe(
      tap(() => this.loadActividadesByPlan(actividad.planId!))
    );
  }

  // Eliminar actividad
  deleteActividad(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      tap(() => {
        // Aquí se necesitaría obtener el planId para recargar, o manejarlo en otro lugar
      })
    );
  }
}
