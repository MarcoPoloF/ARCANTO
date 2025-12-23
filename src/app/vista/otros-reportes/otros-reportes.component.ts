import { Component, OnInit } from '@angular/core';
import {DomSanitizer, Title} from '@angular/platform-browser';
import {UsuarioService} from '../../services/usuario.service';
import {AuthService} from '../../services/auth.service';
import {valueReferenceToExpression} from '@angular/compiler-cli/src/ngtsc/annotations/src/util';
import {Usuario} from '../../model/usuario';
import {ResourcesService} from '../../services/resources.service';
import {CargaArchivoService} from '../../services/carga-archivo.service';
import {CargaArchivo} from '../../model/carga-archivo';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-otros-reportes',
  templateUrl: './otros-reportes.component.html',
  styleUrls: ['./otros-reportes.component.css']
})
export class OtrosReportesComponent implements OnInit {

  constructor(private cargaService: CargaArchivoService,public sanitizer: DomSanitizer,private title: Title, private usuarioService: UsuarioService, private authService: AuthService, public resourceService: ResourcesService) { }
  public usuario:Usuario = new Usuario();
  urlsafe: any;
  public carga1: CargaArchivo = new CargaArchivo();
  public cargas:CargaArchivo [] =[];

  ngOnInit(): void {
    this.title.setTitle("Otros reportes");
    this.cargaService.getbyUsuario(this.authService.usuario.idusuario).subscribe(response=>{
      this.cargas = response as CargaArchivo [];
      }
    );
  }
}
