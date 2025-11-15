export interface Participante {
    id?: number;
    actividadId: string;
    nombre: string;
    cedula: string;
    dependencia: string;
    cargo: string;
    asistio: boolean;
    observaciones?: string;
}