import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { EncargadaDashboard } from './features/dashboard/encargada-dashboard/encargada-dashboard';
import { features } from 'process';
import { ListarActividades } from './features/plans/activities/listar-actividades/listar-actividades';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: Login},
    { path: 'admin-dashboard', loadComponent: () => import('./features/dashboard/admin-dashboard/admin-dashboard').then(m => m.AdminDashboard) },
    { path: 'encargada-dashboard/:planTipo/:planId', loadComponent: () => import('./features/dashboard/encargada-dashboard/encargada-dashboard').then(m=>EncargadaDashboard)},
    { path: 'planes/:id', loadComponent: () => import('./features/plans/details/details').then(m => m.Details) },

    { path: 'planes/:planId/crear-capacitacion', loadComponent: () => import('./features/plans/activities/plan-capacitacion/crear-actividad/crear-actividad').then(m => m.CrearActividadCapacitacion) },
    { path: 'planes/:planId/crear-incentivos', loadComponent: () => import('./features/plans/activities/plan-incentivos/crear-actividad/crear-actividad').then(m => m.CrearActividadIncentivos) },
    
    { path: 'encargada/planes/:planTipo/:planId/crear-capacitacion', loadComponent: () => import('./features/plans/activities/plan-capacitacion/crear-actividad/crear-actividad').then(m => m.CrearActividadCapacitacion) },
    { path: 'encargada/planes/:planTipo/:planId/crear-incentivos', loadComponent: () => import('./features/plans/activities/plan-incentivos/crear-actividad/crear-actividad').then(m => m.CrearActividadIncentivos) },

    // Ruta genérica solo para la jefa/admin
    { path: 'planes/:planId/detalles-actividad/:detallesId', loadComponent: () => import('./features/plans/activities/plan-capacitacion/detalles-actividad/detalles-actividad').then(m => m.DetallesActividadCapacitacion)},

    { path: 'encargada/planes/:planTipo/:planId/actividades', loadComponent: () => import('./features/plans/activities/listar-actividades/listar-actividades').then(m => ListarActividades)},
    { path: 'planes/:planId/detalles-actividad/:detallesId/capacitacion', loadComponent: () => import('./features/plans/activities/plan-capacitacion/detalles-actividad/detalles-actividad').then(m => m.DetallesActividadCapacitacion) },
    { path: 'planes/:planId/detalles-actividad/:detallesId/incentivos', loadComponent: () => import('./features/plans/activities/plan-incentivos/detalles-actividad/detalles-actividad').then(m => m.DetallesActividadIncentivos) },
    

]
