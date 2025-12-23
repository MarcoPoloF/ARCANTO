import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Title} from '@angular/platform-browser';
import {UsuarioService} from '../../services/usuario.service';
import {Usuario} from '../../model/usuario';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public usuario: Usuario = new Usuario();
  constructor(private router: Router, public authService: AuthService, private title: Title, private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.title.setTitle("Dashboard");
  }

  valida(){
    if(this.usuario.cliente.url == ""){
      document.getElementById("f1").classList.add('d-none');
      document.getElementById("f2").classList.remove('d-none')
    }
    else{
      document.getElementById("f1").classList.remove("d-none");
    }
  }
  goPerfil(idusuario: number){
    this.router.navigate(['/perfil/' + idusuario]);
  }
}
