import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, tap, throwError, catchError} from 'rxjs';
import { StorageService } from '../storage/storage.service';

const BASE_URL = 'http://localhost:8080/'
export const AUTH_HEADER = "authorization";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
    private storage: StorageService,
  ) { }

  signup(signupRequest: any): Observable<any> {
    return this.http.post(BASE_URL + "sign-up", signupRequest);
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post(BASE_URL + 'authentication', loginRequest, { observe: 'response' })
      .pipe(
        tap(__ => this.log('User Authentication')),
        catchError(error => {
          console.error('Login failed:', error);
          return throwError(() => error);
        }),
        map((res: HttpResponse<any>) => {
          if (res.body) {
            this.storage.saveUser(res.body);
          }
          const token = res.headers.get(AUTH_HEADER);
          if (token) {
            const tokenLength = token.length;
            const bearerToken = token.substring(7, tokenLength);
            this.storage.saveToken(bearerToken);
          }
          return res;
        })
      );
    }

  log(message: string): void {
    console.log("User Auth Service" + message);
  }
}
