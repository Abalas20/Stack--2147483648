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
    return this.http.post(BASE_URL + 'login', loginRequest, { observe: 'response' })
      .pipe(
        tap(__ => this.log('User Authentication')),
        catchError(error => {
          console.error('Login failed:', error);
          return throwError(() => error);
        }),
        map((res: HttpResponse<any>) => {
          const body = res.body;
          if (body) {
            this.storage.saveUser(body.userId, body.role, body.status);
            this.storage.saveToken(body.jwtToken);
          }
          return res;
        })
      );
  }

  log(message: string): void {
    console.log("User Auth Service" + message);
  }
}

