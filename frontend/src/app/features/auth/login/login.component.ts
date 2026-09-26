import { StorageTokenService } from './../../../core/storage/storage-token.service';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { inject, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { LoginRequest } from '../../../core/models/auth';
import { CommonModule } from '@angular/common';
import { AuthLayoutComponent } from '../../../shared/auth-layout/auth-layout.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { routePourRole } from '../../../core/role-redirect';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    AuthLayoutComponent,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private snackbar = inject(MatSnackBar);
 
  hidePassword = true;
  submitting = false;
 
  form = this.fb.nonNullable.group({
    npi: ['', [Validators.required]],
    password: ['', [Validators.required]],
    rememberPassword: [false],
  });
 
  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload: LoginRequest = {
      npi: this.form.value.npi!,
      password: this.form.value.password!,
    };
    this.submitting = true;
    this.authService.login(payload).subscribe({
      next: () => {
        
        this.submitting = false;
        const userRole = StorageTokenService.getUserRole();
        console.log('User role:', userRole);
        this.router.navigate([routePourRole(userRole)]);
        
      },
      error: () => {
        this.submitting = false;
      }
    });
  }

}

