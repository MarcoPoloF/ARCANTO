import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Token } from './utils/token';
import localeES from '@angular/common/locales/es-Mx';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './vista/home/home.component';
import { LoginComponent } from './vista/login/login.component';
import { PerfilComponent } from './vista/perfil/perfil.component';
import { MenuComponent } from './cabeceras/menu/menu.component';
import { FooterComponent } from './cabeceras/footer/footer.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { DashboardComponent } from './vista/dashboard/dashboard.component';
import { PhantomComponent } from './vista/phantom/phantom.component';
import { SupportComponent } from './vista/support/support.component';
import { OtrosReportesComponent } from './vista/otros-reportes/otros-reportes.component';
import { ModalComponent } from './vista/modal/modal.component';
import { DatePipe } from '@angular/common';
import { AuthInterceptor } from './utils/auth.interceptor';


// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    routingComponents,
    LoginComponent,
    PerfilComponent,
    MenuComponent,
    FooterComponent,
    DashboardComponent,
    PhantomComponent,
    SupportComponent,
    OtrosReportesComponent,
    ModalComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
providers: [
    DatePipe,
    {provide: LOCALE_ID, useValue: 'mx'},
    {provide: HTTP_INTERCEPTORS, useClass: Token, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
