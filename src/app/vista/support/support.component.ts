import { Component, OnInit } from '@angular/core';
import {MailService} from '../../services/mail.service';
import {Title} from '@angular/platform-browser';
import swal from 'sweetalert2';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {

  public nombre: string = "";
  public correo: string = "";
  public mensaje: string = "";
  public describe: string ="";
  constructor(private mailService: MailService, private title: Title) { }

  ngOnInit(): void {
    this.title.setTitle("Soporte técnico");
  }
    sendSupport(){
    if(this.nombre =="" || this.correo =="" || this.mensaje =="" || this.describe ==""){
      swal.fire('','Favor de verificar los campos no deben de estar vacios','warning');
    }else{
      this.mailService.sendMailSupport(this.nombre,this.correo,this.mensaje,this.describe).subscribe(value => {
        swal.fire('','Gracias por comunicarte con nosotros en breve se te brindará la orientación y/o solución requerida','success').then(results =>{
          this.nombre = "";
          this.correo = "";
          this.mensaje = "";
          this.describe = "";
          }
        );
      });
    }
    }
}
