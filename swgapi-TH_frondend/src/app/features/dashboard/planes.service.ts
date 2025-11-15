import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Plan } from './models/plan.model';

@Injectable({
  providedIn: 'root'
})
export class PlanesService {
  private readonly url = 'http://localhost:3000/planes'; //Base de datos fake

  constructor(private http: HttpClient) { }

  getPlanes(): Observable<Plan[]> {
    return this.http.get<Plan[]>(this.url);
  }

  getPlanById(id: string): Observable<Plan> {
    return this.http.get<Plan>(`${this.url}/${id}`);
  }

  updatePlan(plan: Plan): Observable<Plan> {
    return this.http.put<Plan>(`${this.url}/${plan.id}`, plan);
  }
}
