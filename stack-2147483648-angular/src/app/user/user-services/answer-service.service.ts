import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from '../../auth-services/storage/storage.service';
import { Observable } from 'rxjs';

const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class AnswerServiceService {



  constructor(
    private http: HttpClient, 
  ) { }

  postAnswer(answerDTO: any): Observable<any> {
    return this.http.post<[]>(BASIC_URL + 'api/answer', answerDTO,
      { headers: this.createAuthorizationHeader() }
     );
  }

  getAnswerById(id: number): Observable<any> {
    return this.http.get<any>(BASIC_URL + 'api/answer/' + id,
      { headers: this.createAuthorizationHeader() }
    );
  
  }

  deleteAnswerById(idAnswer: number,idUser: number, role: string): Observable<any> {
    return this.http.delete<[]>(BASIC_URL + 'api/answer/' + idAnswer + '/' + idUser + '/' + role,
      { headers: this.createAuthorizationHeader() }
    );
  }

  updateAnswer(answerDTO: any, userId: number, role: string): Observable<any> {
    return this.http.put<[]>(BASIC_URL + 'api/answer/' + answerDTO + '/' + userId + '/' + role, answerDTO,
      { headers: this.createAuthorizationHeader() }
    );
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set('Authorization', 'Bearer ' + StorageService.getToken());
  }
}
