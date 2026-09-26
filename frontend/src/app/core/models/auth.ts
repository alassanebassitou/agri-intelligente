


export type TypeActeur = 'AGRICULTEUR' | 'ACHETEUR' | 'AGENT_ETAT' | 'ADMIN';
 

export const TYPE_ACTEUR_OPTIONS: { value: TypeActeur; label: string }[] = [
  { value: 'AGRICULTEUR', label: 'Agriculteur' },
  { value: 'ACHETEUR', label: 'Acheteur' },
  { value: 'AGENT_ETAT', label: "Agent de l'État" },
];

export interface InscriptionRequest {
  npi: string;
  password: string;
  firstname: string;
  lastname: string;
  phone: string;
  langue: string;
  typeActeur: TypeActeur;
}

export interface LoginRequest {
  npi: string;
  password: string;
}

export interface InscriptionResponse {
  npi: string;
  firstname: string;
  lastname: string;
  phone: string;
  langue: string;
  typeActeur: TypeActeur;
  createdAt: Date;
  updatedAt: Date;
}
