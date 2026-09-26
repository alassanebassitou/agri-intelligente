import { Component } from '@angular/core';
import { InscriptionRequest, TYPE_ACTEUR_OPTIONS } from '../../../core/models/auth';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { inject, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { AuthLayoutComponent } from '../../../shared/auth-layout/auth-layout.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';

/** Valide que password et confirmPassword sont identiques. */
function passwordsMatchValidator(): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    if (!password || !confirmPassword) {
      return null;
    }
    return password === confirmPassword ? null : { passwordsMismatch: true };
  };
}
 
@Component({
  selector: 'app-register',
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
    MatSelectModule,
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {

  private readonly fb = inject(FormBuilder); 
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly snackbar = inject(MatSnackBar);
  readonly typeActeurOptions = TYPE_ACTEUR_OPTIONS;
 
  hidePassword = true;
  hideConfirmPassword = true;
  submitting = false;
 
  form = this.fb.nonNullable.group(
    {
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      npi: ['', [Validators.required]],
      phone: [''],
      typeActeur: ['' as InscriptionRequest['typeActeur'], [Validators.required]],
      langue: ['fr', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]],
      acceptTerms: [false, [Validators.requiredTrue]],
    },
    { validators: passwordsMatchValidator() },
  );
 
  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
 
    const value = this.form.getRawValue();
    const payload: InscriptionRequest = {
      npi: value.npi,
      password: value.password,
      firstname: value.firstname,
      lastname: value.lastname,
      phone: value.phone,
      langue: value.langue,
      typeActeur: value.typeActeur as InscriptionRequest['typeActeur'],
    };
 
    this.submitting = true;    
    this.authService.signUp(payload).subscribe({
      next: (response) => {
        this.snackbar.open('Inscription réussie ! Vous pouvez maintenant vous connecter.', 'Fermer', {
          duration: 5000,
        });
        this.submitting = false;
        this.router.navigate(['/connexion']);
      },
      error: (error) => {
        const errorMessage = error?.error || 'Erreur lors de l\'inscription. Veuillez réessayer.';
        this.snackbar.open(errorMessage, 'Fermer', {
          duration: 5000,
        });
        this.submitting = false;
        console.error('Error during registration:', error);
      }
    });
  }
}
 
