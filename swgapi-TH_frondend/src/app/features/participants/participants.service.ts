import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Participante } from './models/participants.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ParticipantsService {
  private baseUrl = 'http://localhost:3000/participantes';

  //private baseUrl = `${environment.apiUrl}/participantes`;
  private participantesSubject = new BehaviorSubject<Participante[]>([]);
  participantes$ = this.participantesSubject.asObservable();

  constructor(private http: HttpClient){}

  loadParticipantesByActividad(actividadId: string): void {
    this.http.get<Participante[]>(`${this.baseUrl}?actividadId=${actividadId}`)
      .subscribe(participantes => this.participantesSubject.next(participantes));
  }

  getParticipantesByActividad(actividadId: string): Observable<Participante[]> {
    return this.http.get<Participante[]>(`${this.baseUrl}?actividadId=${actividadId}`);
  }

  addParticipante(p: Participante): Observable<Participante> {
    return this.http.post<Participante>(this.baseUrl, p).pipe(
      tap(() => this.loadParticipantesByActividad(p.actividadId))
    );
  }

  updateParticipante(p: Participante): Observable<Participante> {
    return this.http.put<Participante>(`${this.baseUrl}/${p.id}`, p).pipe(
      tap(() => this.loadParticipantesByActividad(p.actividadId))
    );
  }

  deleteParticipante(id: number, actividadId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      tap(() => this.loadParticipantesByActividad(actividadId))
    );
  }
}
