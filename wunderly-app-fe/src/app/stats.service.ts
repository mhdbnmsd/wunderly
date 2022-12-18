import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Stats} from "./short-link-page/Stats";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  constructor(private http: HttpClient) { }

  public getStats(key: String) :Observable<Stats>  {
    const to = new Date()
    const from = new Date();
    to.setDate(to.getDate() + 1);
    from.setDate(to.getDate() - 7);
    return this.http.get<Stats>("http://localhost:8080/stats/" + key+ "/" + this.formateDate(from) + "/" + this.formateDate(to));
  }

  private formateDate(date: Date) :string {
    const mm = date.getMonth() + 1;
    const dd = date.getDate();
    const yyyy = date.getFullYear();
    return yyyy + '-' + mm + '-' + dd;
  }
}
