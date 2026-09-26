import { Injectable } from '@angular/core';
import { StorageTokenService } from '../storage/storage-token.service';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { InscriptionRequest, InscriptionResponse } from '../models/auth';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private url = `${environment.apiUrl}/api/auth`;

  constructor(
    private http: HttpClient,
    private storageTokenService: StorageTokenService
  ) {}

  signUp(user: InscriptionRequest): Observable<HttpResponse<InscriptionResponse>> {
    return this.http.post<InscriptionResponse>(`${this.url}/register`, user, { observe: 'response' });
  }

  login({npi, password}: {npi: string, password: string}): Observable<boolean> {
    const headers = new HttpHeaders().set('contentType', 'application/json')
    return this.http.post(`${this.url}/login`,{npi, password}, {headers, observe: 'response'}).pipe(
      map((res) =>{
        const token = res.headers.get('Authorization')?.substring(7);
        const user = res.body;
        if(token && user){
          this.storageTokenService.saveToken(token);
          this.storageTokenService.saveUser(user);
          return true;
        }

        return false;
      })
    );
  }

  logout(): void {
    this.storageTokenService.saveToken('');
    this.storageTokenService.saveUser(null);
  }
}
