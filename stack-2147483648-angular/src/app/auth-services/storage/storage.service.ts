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
    if (localStorage) {
      localStorage.removeItem(USER_KEY);
      localStorage.setItem(USER_KEY, JSON.stringify(user));
    }
  }

  public saveToken(token: string) {
    if (localStorage) {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.setItem(TOKEN_KEY, token);
    }
  }

  static getToken(): string {
    return localStorage ? localStorage.getItem(TOKEN_KEY) || '' : '';
  }

  static isUserLoggedIn(): boolean {
    return !!this.getToken();
  }

  static logout() {
    if (localStorage) {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_KEY);
    }
  }
}
