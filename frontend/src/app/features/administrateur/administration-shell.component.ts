import { Component, inject } from '@angular/core';
import { DashboardLayoutComponent, DashboardNavItem } from '../../shared/dashboard-layout/dashboard-layout.component';
import { MockDataService } from '../../core/mock/mock-data.service';

@Component({
  selector: 'app-admin-shell',
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
export class AdminShellComponent {
  private readonly mockData = inject(MockDataService);

  // TODO: remplacer par le vrai profil utilisateur une fois l'API /me branchée
  private readonly profile = this.mockData.getUserProfile();
  userName = `${this.profile.firstname} ${this.profile.lastname}`;

  navItems: DashboardNavItem[] = [
    { label: 'Tableau de bord', icon: 'dashboard', route: 'dashboard' },
    { label: 'Utilisateurs', icon: 'group', route: 'utilisateurs' },
    { label: 'Parcelles', icon: 'landscape', route: 'parcelles' },
    { label: 'Alertes', icon: 'notifications_active', route: 'alertes', badgeCount: 9 },
  ];
}