import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { CultureResponse } from '../models/campagne.model';
import { StorageTokenService } from '../storage/storage-token.service';

@Injectable({ providedIn: 'root' })
export class CultureService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/api/cultures`;

  getAll(): Observable<CultureResponse[]> {
    return this.http.get<CultureResponse[]>(this.baseUrl, { headers: this.createdHeaders() });
  }

  private createdHeaders(): HttpHeaders{
    return new HttpHeaders().set(
      'Authorization', 'Bearer '+ StorageTokenService.getToken()
    )
  }
}