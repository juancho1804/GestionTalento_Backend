export interface Actividad {
    id?: number;
    planId?: string;
    nombre: string;
    fecha: string;
    responsable: string;
    lugar: string;
    asignado: number;
    ejecutado: number;
    estado: string;
}

export interface ActividadCapacitacion extends Actividad {
  pagoOrientador?: number;
  pagoTiquetes?: number;
  pagoRefrigerios?: number;
  inscripcion?: number;
  encuestaSatisfaccion?: string;
  informe?: string; // Puede ser url del archivo/foto
}

export interface ActividadBienestar extends Actividad {
  beneficio?: string;
}

export interface ActividadIncentivos extends Actividad {
  personas?: number;
  participantes?: number;
}
