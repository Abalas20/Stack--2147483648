import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth-services/storage/storage.service';

const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private http: HttpClient) { }

  postVote(voteDTO: any): Observable<any> {
    return this.http.post<[]>(BASIC_URL + 'api/voteQuestion', voteDTO,
    { headers: this.createAuthorizationHeader() });
  }

  postVoteA(voteDTO: any): Observable<any> {
    return this.http.post<[]>(BASIC_URL + 'api/voteAnswer', voteDTO,
    { headers: this.createAuthorizationHeader() });
  }

  getVoteCount(questionId: number): Observable<any> {
    return this.http.get<number>(BASIC_URL + 'api/voteCount/' + questionId, 
    { headers: this.createAuthorizationHeader() }
    );
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set('Authorization', 'Bearer ' + StorageService.getToken());
  }
}
