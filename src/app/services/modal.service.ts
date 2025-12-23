import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  public modal = false;
  private _abrirModal = new EventEmitter<any>();
  get abrirModalEvent():EventEmitter<any>{
    return  this._abrirModal;
  }
  set abrirModalEvent(value:EventEmitter<any>){
    this._abrirModal = value;
  }
  abrirModal(){
    this.modal = true;
    this.abrirModalEvent.emit(true);
  }
  cerrarModal(){
    this.modal = false;
  }
  constructor() { }
}
