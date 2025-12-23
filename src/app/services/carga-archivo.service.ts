import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constants} from '../utils/constants/constants';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CargaArchivoService {

  constructor(private http: HttpClient) { }

  getAll():Observable<any>{
    return this.http.get(Constants.HOST+'/carga/getCargas').pipe(
      map(value => {
        return value;
      }),catchError(err => {
        throw err;
      })
    );
  }
  getbyUsuario(idusuario: number){
    return this.http.get(Constants.HOST +'/carga/getArchivos/'+ idusuario).pipe(
      map( value =>{
        return value;
      }),
      catchError( err => {
        throw err;
      })
    );
  }
}
