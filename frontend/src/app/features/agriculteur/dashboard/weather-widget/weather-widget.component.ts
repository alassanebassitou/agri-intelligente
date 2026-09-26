import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { WeatherMock } from '../../../../core/mock/mock-data.service';

@Component({
  selector: 'app-weather-widget',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './weather-widget.component.html',
  styleUrls: ['./weather-widget.component.scss'],
})
export class WeatherWidgetComponent {
  @Input({ required: true }) weather!: WeatherMock;
}