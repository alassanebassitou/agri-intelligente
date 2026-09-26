import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatBadgeModule } from '@angular/material/badge';
import { StorageTokenService } from '../../core/storage/storage-token.service';
import { Router } from '@angular/router';

export interface DashboardNavItem {
  label: string;
  icon: string;
  route: string;
  badgeCount?: number;
}

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    RouterOutlet,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatBadgeModule,
  ],
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.scss'],
})
export class DashboardLayoutComponent {
  @Input() appName = 'AgriSignal';
  @Input() userName = '';
  @Input() navItems: DashboardNavItem[] = [];

  
  constructor(private readonly router: Router) {}
 
  onLogout(): void {
    StorageTokenService.loggout();
    this.router.navigate(['/connexion']);
  }
}