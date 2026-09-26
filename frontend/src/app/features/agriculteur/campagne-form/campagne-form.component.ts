import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CampagneService } from '../../../core/services/campagne.service';
import { CultureService } from '../../../core/services/culture.service';
import { ParcelleService } from '../../../core/services/parcelle.service';
import { CreateCampagneRequest, CultureResponse } from '../../../core/models/campagne.model';
import { ParcelleResponse } from '../../../core/models/parcelle.model';

@Component({
  selector: 'app-campagne-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatNativeDateModule,
    MatSelectModule,
  ],
  templateUrl: './campagne-form.component.html',
  styleUrls: ['./campagne-form.component.scss'],
})
export class CampagneFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly campagneService = inject(CampagneService);
  private readonly cultureService = inject(CultureService);
  private readonly parcelleService = inject(ParcelleService);
  private readonly snackBar = inject(MatSnackBar);

  parcelleId!: number;
  parcelle: ParcelleResponse | null = null;
  cultures: CultureResponse[] = [];

  loadingContext = true;
  submitting = false;

  /** dateSemis doit être passée ou présente — on bloque le sélecteur au-delà d'aujourd'hui. */
  readonly maxDate = new Date();

  form = this.fb.nonNullable.group({
    cultureName: ['', [Validators.required]],
    dateSemis: [null as Date | null, [Validators.required]],
  });

  ngOnInit(): void {
    this.parcelleId = Number(this.route.snapshot.paramMap.get('parcelleId'));

    this.parcelleService.getById(this.parcelleId).subscribe({
      next: (parcelle) => {
        this.parcelle = parcelle;
        this.loadingContext = false;
      },
      error: () => {
        this.loadingContext = false;
        this.snackBar.open('Parcelle introuvable.', 'Fermer', { duration: 4000 });
      },
    });

    this.cultureService.getAll().subscribe({
      next: (cultures) => (this.cultures = cultures),
      error: () => this.snackBar.open('Impossible de charger la liste des cultures.', 'Fermer', { duration: 4000 }),
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    const payload: CreateCampagneRequest = {
      parcelleId: this.parcelleId,
      cultureName: value.cultureName,
      dateSemis: this.toIsoDate(value.dateSemis!),
    };

    this.submitting = true;
    this.campagneService.create(payload).subscribe({
      next: () => {
        this.submitting = false;
        this.snackBar.open('Semis déclaré avec succès.', 'Fermer', { duration: 3000 });
        this.router.navigate(['/accueil/parcelles']);
      },
      error: () => {
        this.submitting = false;
        this.snackBar.open('Erreur lors de la déclaration du semis.', 'Fermer', { duration: 4000 });
      },
    });
  }

  onCancel(): void {
    this.router.navigate(['/accueil/parcelles']);
  }

  /** Convertit le Date du datepicker en 'yyyy-MM-dd' attendu par le LocalDate backend. */
  private toIsoDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}