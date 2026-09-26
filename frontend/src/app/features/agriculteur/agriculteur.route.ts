import { Routes } from '@angular/router';
import { AgriculteurShellComponent } from './agriculteur-shell.component';

export const AGRICULTEUR_ROUTES: Routes = [
  {
    path: '',
    component: AgriculteurShellComponent,
    children: [
      { path: '', redirectTo: 'tableau-de-bord', pathMatch: 'full' },
      {
        path: 'tableau-de-bord',
        loadComponent: () =>
          import('./dashboard/dashboard.component').then((m) => m.DashboardComponent),
      },
      {
        path: 'parcelles',
        loadComponent: () =>
          import('./parcelles/parcelles.component').then((m) => m.ParcellesComponent),
      },
      {
        path: 'parcelles/:parcelleId/nouvelle-campagne',
        loadComponent: () =>
          import('./campagne-form/campagne-form.component').then((m) => m.CampagneFormComponent),
      },
      {
        path: 'formation',
        loadComponent: () =>
          import('./formation/formation.component').then((m) => m.FormationComponent),
      },
      {
        path: 'alertes',
        loadComponent: () =>
          import('./alertes/alertes.component').then((m) => m.AlertesComponent),
      },
    ],
  },
];