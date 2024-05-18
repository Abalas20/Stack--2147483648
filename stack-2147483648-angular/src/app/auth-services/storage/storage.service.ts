import { Injectable } from '@angular/core';

const TOKEN_KEY = 'c_token';
const USER_KEY = 'user_id';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  static hasToken(): boolean {
    return !!this.getToken();
  }

  constructor() { }

  public saveUser(user: any) {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(USER_KEY);
      localStorage.setItem(USER_KEY, JSON.stringify(user));
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

  static isUserLoggedIn(): boolean {
    return !!this.getToken();
  }

  static getUserId(): any {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user).userId : null;
  }

  static logout() {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_KEY);
      }
  }
}
