import { Injectable } from '@angular/core';
import {Link} from "./short-link-page/Link";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LinkService {

  constructor(private http: HttpClient) { }

  public postLink(link: Link) :Observable<Link> {
    return this.http.post<Link>("http://localhost:8080/link", link)
  }

  public getLink(key: String) :Observable<Link> {
    return this.http.get<Link>("http://localhost:8080/link/" + key)
  }

  public deleteLink(key: String) :Observable<void> {
    return this.http.delete<void>("http://localhost:8080/link/" + key)
  }
}
