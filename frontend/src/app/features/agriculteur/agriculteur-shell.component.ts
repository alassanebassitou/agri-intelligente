import { Component, inject } from '@angular/core';
import { DashboardLayoutComponent, DashboardNavItem } from '../../shared/dashboard-layout/dashboard-layout.component';
import { MockDataService } from '../../core/mock/mock-data.service';

@Component({
  selector: 'app-agriculteur-shell',
  standalone: true,
  imports: [DashboardLayoutComponent],
  template: `
    <app-dashboard-layout
      appName="AgriSignal"
      [userName]="userName"
      [navItems]="navItems">
    </app-dashboard-layout>
  `,
})
export class AgriculteurShellComponent {
  private readonly mockData = inject(MockDataService);

  // TODO: remplacer par le vrai profil utilisateur une fois l'API /me branchée
  private readonly profile = this.mockData.getUserProfile();
  userName = `${this.profile.firstname} ${this.profile.lastname}`;

  navItems: DashboardNavItem[] = [
    { label: 'Tableau de bord', icon: 'dashboard', route: 'tableau-de-bord' },
    { label: 'Mes parcelles', icon: 'landscape', route: 'parcelles' },
    { label: 'Formation', icon: 'school', route: 'formation' },
    { label: 'Alertes', icon: 'notifications_active', route: 'alertes', badgeCount: 2 },
  ];
}