import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';

import { CampagneService } from '../../../core/services/campagne.service';
import { ParcelleService } from '../../../core/services/parcelle.service';
import { CreateParcelleRequest, ParcelleResponse, TYPE_SOL_OPTIONS } from '../../../core/models/parcelle.model';

@Component({
  selector: 'app-parcelles',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatTableModule,
    MatTooltipModule,
  ],
  templateUrl: './parcelles.component.html',
  styleUrls: ['./parcelles.component.scss'],
})
export class ParcellesComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly parcelleService = inject(ParcelleService);
  private readonly campagneService = inject(CampagneService);
  private readonly snackBar = inject(MatSnackBar);
  private readonly router = inject(Router);

  readonly typeSolOptions = TYPE_SOL_OPTIONS;
  readonly displayedColumns = ['name', 'superficie', 'commune', 'typeSol', 'createdAt', 'actions'];

  parcelles: ParcelleResponse[] = [];
  totalElements = 0;
  pageIndex = 0;
  pageSize = 5;

  /** Ids des parcelles pour lesquelles une campagne existe déjà — une seule déclaration de semis autorisée par parcelle. */
  parcellesAvecCampagne = new Set<number>();

  loadingList = false;
  submitting = false;
  showForm = false;

  form = this.fb.nonNullable.group({
    name: ['', [Validators.required]],
    superficie: [null as number | null, [Validators.required, Validators.min(0.01)]],
    latitude: [null as number | null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    longitude: [null as number | null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    commune: ['', [Validators.required]],
    typeSol: ['', [Validators.required]],
  });

  ngOnInit(): void {
    this.loadParcelles();
  }

  loadParcelles(): void {
    this.loadingList = true;
    this.parcelleService.getMine(this.pageIndex, this.pageSize).subscribe({
      next: (page) => {
        this.parcelles = page.content;
        this.totalElements = page.totalElements;
        this.loadingList = false;
        this.checkCampagnesExistantes();
      },
      error: () => {
        this.loadingList = false;
        this.snackBar.open('Impossible de charger vos parcelles.', 'Fermer', { duration: 4000 });
      },
    });
  }

  /**
   * Vérifie, pour chaque parcelle affichée, si une campagne a déjà été déclarée.
   * Pas de champ dédié côté ParcelleResponse pour ça, donc on interroge
   * GET /api/campagnes?parcelleId=X pour chacune (acceptable vu la pagination limitée).
   */
  private checkCampagnesExistantes(): void {
    if (this.parcelles.length === 0) {
      this.parcellesAvecCampagne = new Set();
      return;
    }

    const checks = this.parcelles.map((p) =>
      this.campagneService.listForParcelle(p.id).pipe(
        map((campagnes) => ({ id: p.id, hasCampagne: campagnes.length > 0 })),
        catchError(() => of({ id: p.id, hasCampagne: false })),
      ),
    );

    forkJoin(checks).subscribe((results) => {
      this.parcellesAvecCampagne = new Set(
        results.filter((r) => r.hasCampagne).map((r) => r.id),
      );
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadParcelles();
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    if (!this.showForm) {
      this.form.reset();
    }
  }

  declarerSemis(parcelle: ParcelleResponse): void {
    if (this.parcellesAvecCampagne.has(parcelle.id)) {
      return;
    }
    this.router.navigate(['/accueil/parcelles', parcelle.id, 'nouvelle-campagne']);
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    const payload: CreateParcelleRequest = {
      name: value.name,
      superficie: value.superficie!,
      latitude: value.latitude!,
      longitude: value.longitude!,
      commune: value.commune,
      typeSol: value.typeSol,
    };

    this.submitting = true;
    this.parcelleService.create(payload).subscribe({
      next: () => {
        this.submitting = false;
        this.showForm = false;
        this.form.reset();
        this.snackBar.open('Parcelle enregistrée avec succès.', 'Fermer', { duration: 3000 });
        this.pageIndex = 0;
        this.loadParcelles();
      },
      error: () => {
        this.submitting = false;
        this.snackBar.open("Erreur lors de l'enregistrement de la parcelle.", 'Fermer', { duration: 4000 });
      },
    });
  }
}