import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth-services/storage/storage.service';

const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(
    private http: HttpClient
  ) { }

  postQuestion(questionDTO: any): Observable<any> {
    return this.http.post<[]>(BASIC_URL + 'api/question', questionDTO,
      { headers: this.createAuthorizationHeader() }
     );
  }

  getAllQuestions(pageNumber: number): Observable<any> {
    return this.http.get<[]>(BASIC_URL + 'api/questions/' + pageNumber,
      { headers: this.createAuthorizationHeader() }
    );
  }

  getQuestionById(id: number): Observable<any> {
    return this.http.get<[]>(BASIC_URL + 'api/question/' + id,
      { headers: this.createAuthorizationHeader() }
    );
  }

  updateQuestion(questionDto: any, userId: number): Observable<any> {
    return this.http.put<[]>(BASIC_URL + 'api/question/' + userId, questionDto,
      { headers: this.createAuthorizationHeader() }
    );
  }

  getAllQuestionsByText(text: string, pageNumber: number): Observable<any> {
    return this.http.get<[]>(BASIC_URL + 'api/questions/search-by-text/' + text + '/' + pageNumber,
      { headers: this.createAuthorizationHeader() }
    );
  }

  getAllQuestionsByUsername(username: string, pageNumber: number): Observable<any> {
    return this.http.get<[]>(BASIC_URL + 'api/questions/search-by-username/' + username + '/' + pageNumber,
      { headers: this.createAuthorizationHeader() }
    );
  }
  
  getAllQuestionsByTag(tag: string, pageNumber: number): Observable<any> {
    return this.http.get<[]>(BASIC_URL + 'api/questions/search-by-tag/' + tag + '/' + pageNumber,
      { headers: this.createAuthorizationHeader() }
    );
  }

  //Deadline -> No time to make User service
  getUser(id: any): Observable<any> {
    return this.http.get<[]>(BASIC_URL + 'user/' + id,
      { headers: this.createAuthorizationHeader() }
    );
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set('Authorization', 'Bearer ' + StorageService.getToken());
  }


}
