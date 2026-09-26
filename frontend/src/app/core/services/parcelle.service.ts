import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { CreateParcelleRequest, PageResponse, ParcelleResponse } from '../models/parcelle.model';
import { StorageTokenService } from '../storage/storage-token.service';

@Injectable({ providedIn: 'root' })
export class ParcelleService {
  private readonly http = inject(HttpClient);

  private readonly baseUrl = `${environment.apiUrl}/api/parcelles`;

  create(request: CreateParcelleRequest): Observable<ParcelleResponse> {
    return this.http.post<ParcelleResponse>(`${this.baseUrl}/create`, request, { headers: this.createdHeaders() });
  }

  getById(parId: number): Observable<ParcelleResponse> {
    return this.http.get<ParcelleResponse>(`${this.baseUrl}/${parId}`, { headers: this.createdHeaders() });
  }

  /** Toutes les parcelles de la plateforme, paginées (endpoint public/multi-rôle). */
  getAll(page: number, size: number): Observable<PageResponse<ParcelleResponse>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PageResponse<ParcelleResponse>>(this.baseUrl, { params, headers: this.createdHeaders() });
  }

  /** Les parcelles de l'agriculteur connecté, paginées. */
  getMine(page: number, size: number): Observable<PageResponse<ParcelleResponse>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PageResponse<ParcelleResponse>>(`${this.baseUrl}/by-user`, { params, headers: this.createdHeaders() });
  }

  private createdHeaders(): HttpHeaders{
    return new HttpHeaders().set(
      'Authorization', 'Bearer '+ StorageTokenService.getToken()
    )
  }
}