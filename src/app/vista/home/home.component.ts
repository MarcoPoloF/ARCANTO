import { Component, OnInit } from '@angular/core';
import {Title} from '@angular/platform-browser';
import {MailService} from '../../services/mail.service';
import swal from 'sweetalert2';
import { ModalService } from 'src/app/services/modal.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  vista: boolean = true;
  public nombre: string ="";
  public correo: string ="";
  public mensaje: string ="";
  constructor(private title: Title, private mailService: MailService, private modalService:ModalService) { }

  ngOnInit(): void {
    this.title.setTitle("Finanzas Inteligentes");
    this.abrirModal();
  }
    ver(num: number){
    if(num == 0){
      document.getElementById("acc").classList.add("visible");
    }else{
      document.getElementById("acc").classList.remove("invisible");
    }
    }
    abrirModal(){
      this.modalService.abrirModal();
    }

  verpanel(number: number){
   let b=   document.getElementById('panel-' + number).classList.add('active');
   // @ts-ignore
    if(b == 1){

   }
    document.getElementById('s-panel').classList.remove('d-none');

  }

  no(number: number){
   let b= document.getElementById('panel-' + number).classList.remove('active');
    document.getElementById('s-panel').classList.add('d-none');
  }
      mostrarpanel(){
      document.getElementById('Victor-panel').classList.remove('d-none');
        document.getElementById('s-panel').classList.remove('d-none');
        document.getElementById('victor').classList.add('opacity-0');
      }
      mostrar(){
        document.getElementById('services-1').classList.remove('d-none');
        document.getElementById('services').classList.add('d-none');
      }
      mostrarservicio(){
      document.getElementById('services').classList.add('d-none');
      document.getElementById('collapseTwo').classList.add('show');
      document.getElementById('collapseOne').classList.remove('show');
      document.getElementById('services-1').classList.remove('d-none');
      }
    mostrarservicio1(){
    document.getElementById('services').classList.add('d-none');
    document.getElementById('collapseThree').classList.add('show');
    document.getElementById('collapseOne').classList.remove('show');
      document.getElementById('services-1').classList.remove('d-none');
  }
  mostrarservicio2(){
    document.getElementById('services').classList.add('d-none');
    document.getElementById('collapseFour').classList.add('show');
    document.getElementById('collapseOne').classList.remove('show');
    document.getElementById('services-1').classList.remove('d-none');
  }
  mostrarservicio3(){
    document.getElementById('services').classList.add('d-none');
    document.getElementById('collapseFive').classList.add('show');
    document.getElementById('collapseOne').classList.remove('show');
    document.getElementById('services-1').classList.remove('d-none');
  }
  mostrarpanelI(){
        document.getElementById('Ivette-panel').classList.remove('d-none');
        document.getElementById('s-panel').classList.remove('d-none');
        document.getElementById('victor').classList.add('offset-lg-3');
        document.getElementById('ivette').classList.add('d-none');
        document.getElementById('victor').classList.remove('prueba');
      }
      ocultarpanelI(){
        document.getElementById('Ivette-panel').classList.add('d-none');
        document.getElementById('s-panel').classList.add('d-none');
        document.getElementById('ivette').classList.remove('d-none');
        document.getElementById('victor').classList.remove('offset-lg-3');
        document.getElementById('victor').classList.add('prueba');
      }
      mostrarpanelP(){
        document.getElementById('Pablo-panel').classList.remove('d-none');
        document.getElementById('s-panel').classList.remove('d-none');
        document.getElementById('victor').classList.add('offset-lg-3');
        document.getElementById('pablo').classList.add('d-none');
        document.getElementById('victor').classList.remove('prueba');
      }
      ocultarpanelP(){
        document.getElementById('Pablo-panel').classList.add('d-none');
        document.getElementById('s-panel').classList.add('d-none');
        document.getElementById('pablo').classList.remove('d-none');
        document.getElementById('victor').classList.remove('offset-lg-3');
        document.getElementById('victor').classList.add('prueba');
      }
      ocultarpanel(){
        document.getElementById('Victor-panel').classList.add('d-none');
        document.getElementById('s-panel').classList.add('d-none');
        document.getElementById('victor').classList.remove('opacity-0');
        document.getElementById('victor').classList.add('prueba');
      }
      vistacomponente(){
      this.vista = true;
      }
      ocultacomponente(divToShow: string){
        this.vista = false;
        let elements = document.getElementsByClassName('collapse');
        for (let i = 0 ; i < elements.length; i++){
          elements[i].classList.remove('show');
        }
        document.getElementById(divToShow).classList.add('show');
      }
      sendMail(){
        if(this.nombre == "" || this.correo =="" || this.mensaje ==""){
          swal.fire('', 'Favor de llenar todos los campos','warning');
        }else{
          this.mailService.sendMailContacto(this.nombre,this.correo,this.mensaje).subscribe(value => {
            console.log(value);
            swal.fire('','Gracias por contactarnos en breve alguno de nuestros asesores se comunicará contigo vía correo electrónico','success').then(result =>{
              this.nombre ="";
              this.correo = "";
              this.mensaje = "";
            });
          });
        }
      }
}
