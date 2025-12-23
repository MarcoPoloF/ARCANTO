import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {ResourcesService} from '../../services/resources.service';
import {Usuario} from '../../model/usuario';
import {UsuarioService} from '../../services/usuario.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  isLogged = false;
  nombre:string="";
  id:number=0;
  src: any;
  public  usuario: Usuario = new Usuario();

  constructor(private router: Router,private usuarioService: UsuarioService,  public autoservicio: AuthService, public resourceService: ResourcesService) { }

  ngOnInit(): void {
    this.isLogged = this.autoservicio.isAuthenticated();
    }
  checkurl(){
  if(this.router.url.indexOf("login") != -1){
    return false;
  }else if(this.router.url.indexOf("dashboard") != -1){
    return false;
  }
  else{
    return  true;
  }
  }
  OnLogOut(): void {
    this.autoservicio.logout();
    this.router.navigate(['/home']);
  }
}
