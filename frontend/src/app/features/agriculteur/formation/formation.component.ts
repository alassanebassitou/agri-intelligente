import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-formation',
  standalone: true,
  imports: [MatCardModule, MatIconModule],
  template: `
    <h1 class="dash-title">Formation</h1>
    <p class="dash-subtitle">Renseignez-vous et formez-vous aux bonnes pratiques agricoles</p>
    <mat-card class="stub-card">
      <mat-icon>school</mat-icon>
      <p>Liste des modules de formation — à venir</p>
    </mat-card>
  `,
  styles: [`
    .dash-title { font-size: 24px; font-weight: 600; margin: 0 0 4px; }
    .dash-subtitle { font-size: 14px; color: rgba(0,0,0,.6); margin: 0 0 28px; }
    .stub-card { padding: 48px; text-align: center; box-shadow: none; border: 1px dashed #e3e6e1; border-radius: 8px; }
    mat-icon { font-size: 32px; width: 32px; height: 32px; color: rgba(0,0,0,.25); margin-bottom: 8px; }
  `],
})
export class FormationComponent {}