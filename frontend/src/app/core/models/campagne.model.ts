
export interface CreateCampagneRequest {
  parcelleId: number;
  cultureName: string;
  dateSemis: string;
}

export interface CampagneResponse {
  id: number;
  parcelleId: number;
  cultureNom: string;
  dateSemis: string;
  dateRecolteEstimee: string;
  statut: string;
  quantiteRecoltee: number | null;
}

export type StatutCloture = 'RECOLTEE' | 'ABANDONNEE';

export interface ClotureCampagneRequest {
  status: StatutCloture;
  quantiteRecoltee: number | null;
}

export interface CultureResponse {
  cultureId: number;
  cultureName: string;
}