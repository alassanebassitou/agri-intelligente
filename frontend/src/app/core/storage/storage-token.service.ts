import { Injectable } from '@angular/core';

const TOKEN = 'token';
const USER = 'user';

@Injectable({
  providedIn: 'root'
})
export class StorageTokenService {

  constructor(){}

    public saveToken(token: string){
        window.localStorage.removeItem(TOKEN);
        window.localStorage.setItem(TOKEN, token);
    }

    public saveUser(user: any){
        window.localStorage.removeItem(USER);
        window.localStorage.setItem(USER, JSON.stringify(user));
    }

    static getToken(): string{
        return localStorage.getItem(TOKEN) || '';
    }

    static getUser(): any{
      const item = localStorage.getItem(USER);
        return item ? JSON.parse(item) : null;
    }

    static getUserId(): string{
        const user = this.getUser();
        if( user == null){
            return '';
        }
        return user.userId;
    }

    static getUserRole(): string{
        const user = this.getUser();
        if( user == null){
            return '';
        }
        return user.role;
    }

    static isLoggedIn(): boolean {
        return !!this.getToken();
    }

    static hasRole(role: string): boolean {
        return this.isLoggedIn() && this.getUserRole() === role;
    }

    static loggout(){
        window.localStorage.removeItem(TOKEN);
        window.localStorage.removeItem(USER);
    }
}
