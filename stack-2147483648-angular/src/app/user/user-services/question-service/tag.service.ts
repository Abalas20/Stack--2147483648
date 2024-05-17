import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { StorageService } from '../../../auth-services/storage/storage.service';

const BASIC_URL = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(
    private http: HttpClient
  ) {}

  getTags(): Observable<string[]> {
    const headers = {
      Authorization: 'Bearer ' + StorageService.getToken()
    };
    return this.http.get<{ tagName: string }[]>(BASIC_URL + 'tags', { headers }).pipe(
      map(tags => tags.map(tag => tag.tagName))
    );
  }
}
