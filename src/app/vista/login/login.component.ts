import { Component, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import swal from 'sweetalert2';
import {Usuario} from "../../model/usuario";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public usuario: Usuario = new Usuario();
  constructor(private title: Title, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    if(this.authService.isAuthenticated()){
      this.router.navigate(['/dashboard']);
    }
    this.title.setTitle("Iniciar sesión");
  }
  login(){
    if(this.usuario.correo == null || this.usuario.contrasena == null ||this.usuario.correo == '' || this.usuario.contrasena == ''){
      swal.fire('Error','Email o contraseña vacios','error');
      return;
    }
    this.authService.login(this.usuario).subscribe(response => {
      this.authService.guardarUsuario(response.access_token);
      this.authService.guardarToken(response.access_token);
      this.authService.guardarRefreshToken(response.refresh_token);
      this.router.navigate(['/dashboard']);
    }, error => {
      if (error.status == 400) {
        swal.fire('Error', 'Vaya algo salio mal favor de contactar al administrador', 'error');
      }
    });
  }

}
