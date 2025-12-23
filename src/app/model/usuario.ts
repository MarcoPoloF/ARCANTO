import {Cliente} from "./cliente";

export class Usuario {
  idusuario:number = 0;
  nombre_c: string = "";
  correo: string = "";
  contrasena: string ="";
  rolList: string[] = [];
  cliente: Cliente =new Cliente();
}
