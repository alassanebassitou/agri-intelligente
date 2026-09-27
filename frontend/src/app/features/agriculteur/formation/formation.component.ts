import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { FormationModuleMock, MockDataService } from '../../../core/mock/mock-data.service';

@Component({
  selector: 'app-formation',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatCardModule, MatIconModule],
  templateUrl: './formation.component.html',
  styleUrls: ['./formation.component.scss'],
})
export class FormationComponent {
  private readonly mockData = inject(MockDataService);

  modules: FormationModuleMock[] = this.mockData.getFormationModules();

  get nombreTermines(): number {
    return this.modules.filter((m) => m.statut === 'Terminé').length;
  }
}