import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { Observable } from "rxjs";
import {Injectable} from "@angular/core";
import { AuthService } from "../services/auth.service";
@Injectable({
  providedIn: 'root'
})
export class Token implements HttpInterceptor{
  constructor(private authService: AuthService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.token;
    if(token != null){
      if(req.url.indexOf('oauth/token') <= -1){
        const authReq = req.clone({
          headers: req.headers.set('Authorization', 'Bearer ' + token)
        });
        return next.handle(authReq);

      }
    }
    return next.handle(req);
  }
}
