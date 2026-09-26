import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-alertes',
  standalone: true,
  imports: [MatCardModule, MatIconModule],
  template: `
    <h1 class="dash-title">Alertes</h1>
    <p class="dash-subtitle">Alertes climatiques et phytosanitaires sur vos parcelles</p>
    <mat-card class="stub-card">
      <mat-icon>notifications_active</mat-icon>
      <p>Aucune alerte pour le moment</p>
    </mat-card>
  `,
  styles: [`
    .dash-title { font-size: 24px; font-weight: 600; margin: 0 0 4px; }
    .dash-subtitle { font-size: 14px; color: rgba(0,0,0,.6); margin: 0 0 28px; }
    .stub-card { padding: 48px; text-align: center; box-shadow: none; border: 1px dashed #e3e6e1; border-radius: 8px; }
    mat-icon { font-size: 32px; width: 32px; height: 32px; color: rgba(0,0,0,.25); margin-bottom: 8px; }
  `],
})
export class AlertesComponent {}