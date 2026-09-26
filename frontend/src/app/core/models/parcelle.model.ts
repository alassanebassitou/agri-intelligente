// Doit rester synchronisé avec les DTOs backend CreateParcelleRequest / ParcelleResponse.

export interface CreateParcelleRequest {
  name: string;
  superficie: number;
  latitude: number;
  longitude: number;
  commune: string;
  typeSol: string;
}

export interface ParcelleResponse {
  id: number;
  name: string;
  superficie: number;
  latitude: number;
  longitude: number;
  commune: string;
  typeSol: string;
  createdAt: string; // LocalDateTime sérialisé en ISO string par Jackson
  userFullname: string;
  updatedAt: string;
}

/** Reflète la structure JSON d'un org.springframework.data.domain.Page. */
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number; // page courante, 0-indexée — correspond directement à MatPaginator.pageIndex
  size: number;
  first: boolean;
  last: boolean;
}

/** Options proposées dans le select "Type de sol" du formulaire de création. */
export const TYPE_SOL_OPTIONS: string[] = [
  'Argileux',
  'Sableux',
  'Limoneux',
  'Latéritique',
  'Ferralitique',
  'Argilo-sableux',
  'Autre',
];