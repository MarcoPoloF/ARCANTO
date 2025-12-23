import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Constants} from "../utils/constants/constants";
import {HttpClient} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private httpClient: HttpClient) { }

  getUsuario(idUsuario: number): Observable<any> {
    return this.httpClient.get(Constants.HOST + '/usuario/getById/' + idUsuario).pipe(
      map(value => {
        return value;
      }),
      catchError(err => {
        throw err;
      })
    );
  }
}
