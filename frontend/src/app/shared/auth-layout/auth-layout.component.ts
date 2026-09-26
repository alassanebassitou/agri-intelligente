import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-auth-layout',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule],
  templateUrl: './auth-layout.component.html',
  styleUrls: ['./auth-layout.component.scss'],
})
export class AuthLayoutComponent {
  /** 'dark' = fond marine (login), 'purple' = fond violet (inscription). */
  @Input() background: 'green-dark' | 'green-light' = 'green-dark';
 
  /** Nom d'une icône Material, affichée dans le badge coloré au-dessus du titre. */
  @Input() icon = 'hexagon';
 
  @Input() title = '';
  @Input() subtitle = '';
 
  /** Largeur maximale de la carte. */
  @Input() cardMaxWidth = '480px';
}
