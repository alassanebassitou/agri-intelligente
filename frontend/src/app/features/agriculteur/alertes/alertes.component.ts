import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { AlerteMock, MockDataService, TypeAlerte } from '../../../core/mock/mock-data.service';

const ICONE_PAR_TYPE: Record<TypeAlerte, string> = {
  climat: 'thunderstorm',
  phytosanitaire: 'bug_report',
  planification: 'event_available',
};

const LABEL_PAR_TYPE: Record<TypeAlerte, string> = {
  climat: 'Climat',
  phytosanitaire: 'Phytosanitaire',
  planification: 'Planification',
};

@Component({
  selector: 'app-alertes',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule],
  templateUrl: './alertes.component.html',
  styleUrls: ['./alertes.component.scss'],
})
export class AlertesComponent {
  private readonly mockData = inject(MockDataService);

  alertes: AlerteMock[] = this.mockData.getAlertes();

  icone(type: TypeAlerte): string {
    return ICONE_PAR_TYPE[type];
  }

  label(type: TypeAlerte): string {
    return LABEL_PAR_TYPE[type];
  }
}