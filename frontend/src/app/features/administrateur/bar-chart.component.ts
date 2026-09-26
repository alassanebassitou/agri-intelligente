import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { InscriptionMoisMock } from '../../core/mock/mock-data.service';

@Component({
  selector: 'app-bar-chart',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="bar-chart">
      <div class="bar-col" *ngFor="let point of data">
        <div class="bar-track">
          <div class="bar-fill" [style.height.%]="heightPercent(point.total)"></div>
        </div>
        <span class="bar-value">{{ point.total }}</span>
        <span class="bar-label">{{ point.mois }}</span>
      </div>
    </div>
  `,
  styles: [`
    .bar-chart {
      display: flex;
      align-items: flex-end;
      gap: 16px;
      height: 180px;
      padding-top: 8px;
    }
    .bar-col {
      display: flex;
      flex-direction: column;
      align-items: center;
      flex: 1;
      height: 100%;
    }
    .bar-track {
      flex: 1;
      width: 100%;
      display: flex;
      align-items: flex-end;
      max-width: 32px;
      margin: 0 auto;
    }
    .bar-fill {
      width: 100%;
      background: #2d5f3e;
      border-radius: 4px 4px 0 0;
      transition: height 0.3s ease;
    }
    .bar-value {
      font-size: 12px;
      font-weight: 600;
      color: rgba(0, 0, 0, 0.7);
      margin-top: 6px;
    }
    .bar-label {
      font-size: 12px;
      color: rgba(0, 0, 0, 0.5);
    }
  `],
})
export class BarChartComponent {
  @Input({ required: true }) data!: InscriptionMoisMock[];

  heightPercent(value: number): number {
    const max = Math.max(...this.data.map((d) => d.total), 1);
    return (value / max) * 100;
  }
}