import { Injectable } from '@angular/core';

const TOKEN_KEY = 'c_token';
const USER_KEY = 'user_id';
const USER_ROLE = 'user_role';
const USER_STATUS = 'user_status';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  static hasToken(): boolean {
    return !!this.getToken();
  }

  constructor() { }

  public saveUser(userId: any, role: any, status: any) {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(USER_KEY);
      localStorage.setItem(USER_KEY, JSON.stringify(userId));
      localStorage.setItem(USER_ROLE, role);
      localStorage.setItem(USER_STATUS, status);
    }
  }

  public saveToken(token: string) {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.setItem(TOKEN_KEY, token);
    }
  }

  static getToken(): string {
    return typeof localStorage !== 'undefined' ? localStorage.getItem(TOKEN_KEY) || '' : '';
  }


  static getUserRole(): string {
    return typeof localStorage !== 'undefined' ? localStorage.getItem(USER_ROLE) || '' : '';
  }

  static isUserLoggedIn(): boolean {
    return !!this.getToken();
  }

  static getUserId(): any {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
  }

  static logout() {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_KEY);
      localStorage.removeItem(USER_ROLE);
      localStorage.removeItem(USER_STATUS);
      }
  }
}
