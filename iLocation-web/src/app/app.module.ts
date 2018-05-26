import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';

import { MapComponent } from './map/map.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpService } from './http/http.service';
import { HttpModule } from '@angular/http';
import { CookieService } from './cookie/cookie.service';
import { AppConfigService } from './app-config.service';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [
    HttpService,
    AppConfigService,
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
