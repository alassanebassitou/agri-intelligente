import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { DashboardKpiData, MockDataService, ParcelleMock, WeatherMock } from '../../../core/mock/mock-data.service';
import { WeatherWidgetComponent } from './weather-widget/weather-widget.component';

@Component({
  selector: 'app-agriculteur-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, WeatherWidgetComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {
  private readonly mockData = inject(MockDataService);

  kpis: DashboardKpiData[] = this.mockData.getDashboardKpis();
  parcelles: ParcelleMock[] = this.mockData.getParcelles();
  weather: WeatherMock = this.mockData.getWeather();
}