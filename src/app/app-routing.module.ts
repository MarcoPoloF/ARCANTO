import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './utils/auth.guard';
import {HomeComponent} from './vista/home/home.component';
import {LoginComponent} from './vista/login/login.component';
import {PerfilComponent} from './vista/perfil/perfil.component';
import {DashboardComponent} from './vista/dashboard/dashboard.component';
import {SupportComponent} from './vista/support/support.component';
import {PhantomComponent} from './vista/phantom/phantom.component';
import {OtrosReportesComponent} from './vista/otros-reportes/otros-reportes.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent, data:{title: 'Finanzas Inteligentes'}},
  {path: 'login', component: LoginComponent, data: {title: 'Iniciar sesión'}},
  {path: 'perfil', component: PerfilComponent, data:{title: 'Dashboard inteligente'}, canActivate: [AuthGuard]},
  {path: 'dashboard', component: DashboardComponent, data:{title: 'Dashboard'}, canActivate:[AuthGuard]},
  {path: 'support', component: SupportComponent, data:{title: 'Soporte FI'},canActivate: [AuthGuard]},
  {path: 'phantom', component: PhantomComponent, data:{title: 'Reporte financiero mensual'}, canActivate:[AuthGuard]},
  {path: 'otros-reportes', component: OtrosReportesComponent, data:{title: 'Otros resportes'}, canActivate:[AuthGuard]},
  {path: '**', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{
    onSameUrlNavigation: "ignore",
    anchorScrolling:'enabled',
    scrollPositionRestoration: 'enabled'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {  }
export const routingComponents = [HomeComponent, LoginComponent, PerfilComponent, OtrosReportesComponent];
