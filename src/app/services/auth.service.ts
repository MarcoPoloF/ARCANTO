import { Injectable } from '@angular/core';
import {Usuario} from "../model/usuario";
import {Observable} from "rxjs";
import {Constants} from "../utils/constants/constants";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  //@ts-ignore
  private _usuario: Usuario;
  // @ts-ignore
  private _token: string;
  // @ts-ignore
  private _tokenRefresh: string;

  constructor(private httpClient: HttpClient) {
  }

  get usuario(): Usuario {
    if(localStorage.getItem('usuario') != null){
      // @ts-ignore
      return JSON.parse(localStorage.getItem('usuario')) as Usuario;
    }
    /*let dataUsuario = sessionStorage.getItem('usuario');
    if(dataUsuario != null){
      return JSON.parse(dataUsuario) as Usuario;
    }*/
    return new Usuario();
  }

  set usuario(value: Usuario) {
    this._usuario = value;
  }

  guardarUsuario(accessToken: string) {
    const payload = this.obtenerDatosToken(accessToken);
    this._usuario = new Usuario();
    this._usuario.correo = payload.user_name;
    this._usuario.nombre_c = payload.nombre_c;
    this._usuario.idusuario = payload.idusuario;
    this._usuario.rolList = payload.authorities;
    localStorage.setItem('usuario', JSON.stringify(this._usuario));
    //  sessionStorage.setItem('usuario', JSON.stringify(this._usuario));
  }


  /*Notas se modifica linea 54 session por localStorage*/
  get token(): string {
    if (this._token != null) {
      return this._token;
    } else if (this._token == null && localStorage.getItem('token') != null) {
      // @ts-ignore
      this._token = localStorage.getItem('token');
      return this._token;
    }
    //@ts-ignore
    return null;
  }

  set token(value: string) {
    this._token = value;
  }
  /*Se modifican los sessionStorage por localStorage*/
  get tokenRefresh(): string {
    if (this._tokenRefresh != null) {
      return this._tokenRefresh;
    } else if (this._tokenRefresh == null && localStorage.getItem('tokenRefresh') != null) {
      //@ts-ignore
      this._tokenRefresh = localStorage.getItem('tokenRefresh');
      return this._tokenRefresh;
    }
    //@ts-ignore
    return null;
  }

  set tokenRefresh(value: string) {
    this._tokenRefresh = value;
  }

  upgradeToken(): Observable<any> {
    const urlEndpoint = Constants.HOST + '/oauth/token';
    const credentials = btoa(Constants.APPNAME + ':' + Constants.APPPASSWORD);
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + credentials
    });
    const params = new URLSearchParams();
    params.set('grant_type', 'refresh_token');
    params.set('refresh_token', this.tokenRefresh);
    return this.httpClient.post<any>(urlEndpoint, params.toString(), {headers: httpHeaders});
  }

  //Se modifica session por local
  guardarToken(accessToken: string) {
    this._token = accessToken;
    localStorage.setItem('token', this._token);
  }
  //se modifica sesion por local
  guardarRefreshToken(refreshToken: any) {
    this._tokenRefresh = refreshToken;
    localStorage.setItem('tokenRefresh', this._tokenRefresh);
  }

  public obtenerDatosToken(accessToken: string) {
    if (accessToken != null) {
      return JSON.parse(this.b64DecodeUnicode(accessToken.split('.')[1]));
    }
    return null;
  }


  login(usuario: Usuario): Observable<any> {
    const urlEndpoint = Constants.HOST + '/oauth/token';
    const credentials = btoa(Constants.APPNAME + ':' + Constants.APPPASSWORD);
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + credentials
    });
    const params = new URLSearchParams();
    params.set('grant_type', 'password');
    params.set('password', usuario.contrasena);
    params.set('username', usuario.correo);
    return this.httpClient.post<any>(urlEndpoint, params.toString(), {headers: httpHeaders});
  }

  logout() {
    //@ts-ignore
    this._token = null;
    //@ts-ignore
    this._usuario = null;
    localStorage.clear();
  }
  private b64DecodeUnicode(str: string) {
    // Going backwards: from bytestream, to percent-encoding, to original string.
    // tslint:disable-next-line:only-arrow-functions
    return decodeURIComponent(atob(str).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
  }

  isAuthenticated() {
    const payload = this.obtenerDatosToken(this.token);
    return !!(payload != null && payload.user_name && payload.user_name.length > 0);
  }

  /*hasRole(role: string) {
    if(this.usuario.roles){
      if (this.usuario.roles.includes(role)) {
        return true;
      }
    }
    return false;
  }*/



  updateUsuarioStorage(rutaProfileImage: string) {
    /*const usuarioNew = this.usuario;
    usuarioNew.rutaProfileImage = rutaProfileImage;
    sessionStorage.setItem('usuario', JSON.stringify(usuarioNew));*/
  }
}
