import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Constants} from '../utils/constants/constants';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MailService {

  constructor(private http: HttpClient) { }
  //Mail general contacto
  sendMailContacto (nombre: string, correo: string, mensaje){
    let data = {
      nombre: nombre,
      correo: correo,
      mensaje: mensaje
    }
    return this.http.post(Constants.HOST +'/mail/sendMail', data).pipe(
      map( value =>{
        return value;
      }),
      catchError( err => {
        throw err;
      })
    );
  }
  /*Mail ayuda panel perfil*/
  sendMailSupport (nombre: string, correo: string, mensaje: string,describe: string){
    let data = {
      nombre: nombre,
      correo: correo,
      mensaje: mensaje,
      describe: describe
    }
    return this.http.post(Constants.HOST +'/mail/sendMailSupport', data).pipe(
      map( value =>{
        return value;
      }),
      catchError( err => {
        throw err;
      })
    );
  }
}
