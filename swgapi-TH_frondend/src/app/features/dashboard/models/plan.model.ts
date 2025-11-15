import { Actividad } from "../../plans/models/actividad.model";

export interface Plan {
    id: number;
    name: string;
    presupuestoAsignado: number;
    presupuestoDisponible: number;
    presupuestoEjecutado?: number;
    actividadesTotales: number;
    actividades: Actividad[];
}