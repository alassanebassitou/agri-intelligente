import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { CampagneResponse, ClotureCampagneRequest, CreateCampagneRequest } from '../models/campagne.model';
import { StorageTokenService } from '../storage/storage-token.service';

@Injectable({ providedIn: 'root' })
export class CampagneService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/api/campagnes`;

  create(request: CreateCampagneRequest): Observable<CampagneResponse> {
    return this.http.post<CampagneResponse>(this.baseUrl, request, { headers: this.createdHeaders() });
  }

  listForParcelle(parcelleId: number): Observable<CampagneResponse[]> {
    const params = new HttpParams().set('parcelleId', parcelleId);
    return this.http.get<CampagneResponse[]>(this.baseUrl, { params, headers: this.createdHeaders() });
  }

  cloturer(id: number, request: ClotureCampagneRequest): Observable<CampagneResponse> {
    return this.http.patch<CampagneResponse>(`${this.baseUrl}/${id}/statut`, request, { headers: this.createdHeaders() });
  }

  private createdHeaders(): HttpHeaders{
    return new HttpHeaders().set(
      'Authorization', 'Bearer '+ StorageTokenService.getToken()
    )
  }
}