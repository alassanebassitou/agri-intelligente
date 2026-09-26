import { Routes } from '@angular/router';
import { Component } from '@angular/core';
import { AdminShellComponent } from './administration-shell.component';

// Stubs rapides — même gabarit que les pages agriculteur, à remplir après la démo.
@Component({
  selector: 'app-admin-utilisateurs',
  standalone: true,
  template: `
    <h1 class="dash-title">Utilisateurs</h1>
    <p class="dash-subtitle">Gestion des comptes de la plateforme</p>
    <p>Vue détaillée à venir — voir le tableau "Utilisateurs récemment inscrits" sur le tableau de bord en attendant.</p>
  `,
  styles: [`
    .dash-title { font-size: 24px; font-weight: 600; margin: 0 0 4px; }
    .dash-subtitle { font-size: 14px; color: rgba(0,0,0,.6); margin: 0 0 16px; }
  `],
})
class AdminUtilisateursStubComponent {}

@Component({
  selector: 'app-admin-parcelles',
  standalone: true,
  template: `
    <h1 class="dash-title">Parcelles</h1>
    <p class="dash-subtitle">Vue globale des parcelles enregistrées sur la plateforme</p>
    <p>Vue détaillée à venir.</p>
  `,
  styles: [`
    .dash-title { font-size: 24px; font-weight: 600; margin: 0 0 4px; }
    .dash-subtitle { font-size: 14px; color: rgba(0,0,0,.6); margin: 0 0 16px; }
  `],
})
class AdminParcellesStubComponent {}

@Component({
  selector: 'app-admin-alertes',
  standalone: true,
  template: `
    <h1 class="dash-title">Alertes</h1>
    <p class="dash-subtitle">Alertes climatiques et phytosanitaires actives sur la plateforme</p>
    <p>Vue détaillée à venir.</p>
  `,
  styles: [`
    .dash-title { font-size: 24px; font-weight: 600; margin: 0 0 4px; }
    .dash-subtitle { font-size: 14px; color: rgba(0,0,0,.6); margin: 0 0 16px; }
  `],
})
class AdminAlertesStubComponent {}

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminShellComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./dashboard/dashboard.component').then((m) => m.AdminDashboardComponent),
      },
      { path: 'utilisateurs', component: AdminUtilisateursStubComponent },
      { path: 'parcelles', component: AdminParcellesStubComponent },
      { path: 'alertes', component: AdminAlertesStubComponent },
    ],
  },
];