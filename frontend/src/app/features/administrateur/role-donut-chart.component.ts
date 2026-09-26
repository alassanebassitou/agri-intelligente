import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RepartitionRoleMock } from '../../core/mock/mock-data.service';

@Component({
  selector: 'app-role-donut-chart',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="donut-wrapper">
      <div class="donut" [style.background]="conicGradient()">
        <div class="donut-hole">
          <span class="donut-total">{{ total() }}</span>
          <span class="donut-total-label">Total</span>
        </div>
      </div>

      <ul class="donut-legend">
        <li *ngFor="let item of data">
          <span class="legend-dot" [style.background]="item.color"></span>
          {{ item.role }} · {{ item.count }}
        </li>
      </ul>
    </div>
  `,
  styles: [`
    .donut-wrapper {
      display: flex;
      align-items: center;
      gap: 24px;
    }
    .donut {
      width: 140px;
      height: 140px;
      border-radius: 50%;
      flex-shrink: 0;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .donut-hole {
      width: 88px;
      height: 88px;
      border-radius: 50%;
      background: #ffffff;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    }
    .donut-total {
      font-size: 22px;
      font-weight: 700;
      color: rgba(0, 0, 0, 0.87);
    }
    .donut-total-label {
      font-size: 11px;
      color: rgba(0, 0, 0, 0.5);
    }
    .donut-legend {
      list-style: none;
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      gap: 10px;
      font-size: 13px;
      color: rgba(0, 0, 0, 0.75);
    }
    .legend-dot {
      display: inline-block;
      width: 10px;
      height: 10px;
      border-radius: 50%;
      margin-right: 8px;
    }
  `],
})
export class RoleDonutChartComponent {
  @Input({ required: true }) data!: RepartitionRoleMock[];

  total(): number {
    return this.data.reduce((sum, item) => sum + item.count, 0);
  }

  conicGradient(): string {
    const total = this.total();
    let cumulative = 0;
    const stops = this.data.map((item) => {
      const start = (cumulative / total) * 360;
      cumulative += item.count;
      const end = (cumulative / total) * 360;
      return `${item.color} ${start}deg ${end}deg`;
    });
    return `conic-gradient(${stops.join(', ')})`;
  }
}