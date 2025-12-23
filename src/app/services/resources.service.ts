import { Injectable } from '@angular/core';
import {Constants} from "../utils/constants/constants";

@Injectable({
  providedIn: 'root'
})
export class ResourcesService {

  constructor() { }
  downloadResource(ruta: string){
    return Constants.HOST +"/resources/download/"+ruta;
  }
  descargaDocumento(ruta: string){
    return Constants.HOST +"/resources/descarga/"+ruta;
  }
  Verdocumento(ruta: string){
    return Constants.HOST +"/resources/view/"+ruta;
  }
}
