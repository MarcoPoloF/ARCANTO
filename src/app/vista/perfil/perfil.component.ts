import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DomSanitizer, Title} from "@angular/platform-browser";
import { Usuario } from 'src/app/model/usuario';
import { AuthService } from 'src/app/services/auth.service';
import {UsuarioService} from "../../services/usuario.service";
import {ResourcesService} from '../../services/resources.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  public usuario: Usuario = new Usuario();
  constructor(private title: Title, private usuarioService: UsuarioService, private authService: AuthService
  , public sanitizer: DomSanitizer, public resourceService: ResourcesService) { }

  urlsafe: any;

  ngOnInit(): void {
    this.title.setTitle("Dashboard inteligente");
    this.usuarioService.getUsuario(this.authService.usuario.idusuario).subscribe(value => {
      this.usuario = value as Usuario;
      this.urlsafe = this.sanitizer.bypassSecurityTrustResourceUrl(this.usuario.cliente.url);
    });
  }
}
