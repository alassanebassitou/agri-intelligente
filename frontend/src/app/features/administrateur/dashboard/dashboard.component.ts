import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatChipsModule } from '@angular/material/chips';
import { MatMenuModule } from '@angular/material/menu';


import {
  AdminKpiData,
  InscriptionMoisMock,
  MockDataService,
  RepartitionRoleMock,
  UtilisateurRecentMock,
} from '../../../core/mock/mock-data.service';
import { BarChartComponent } from '../bar-chart.component';
import { RoleDonutChartComponent } from '../role-donut-chart.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatMenuModule,
    BarChartComponent,
    RoleDonutChartComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class AdminDashboardComponent {
  private readonly mockData = inject(MockDataService);

  kpis: AdminKpiData[] = this.mockData.getAdminKpis();
  inscriptions: InscriptionMoisMock[] = this.mockData.getInscriptionsParMois();
  repartitionRoles: RepartitionRoleMock[] = this.mockData.getRepartitionParRole();
  utilisateursRecents: UtilisateurRecentMock[] = this.mockData.getUtilisateursRecents();

  searchTerm = '';

  get utilisateursFiltres(): UtilisateurRecentMock[] {
    if (!this.searchTerm.trim()) {
      return this.utilisateursRecents;
    }
    const term = this.searchTerm.toLowerCase();
    return this.utilisateursRecents.filter(
      (u) => u.nom.toLowerCase().includes(term) || u.role.toLowerCase().includes(term),
    );
  }
}