import { Injectable } from '@angular/core';
import { HttpService } from '../http/http.service';
import { AppConfigService } from '../app-config.service';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  private url: string;

  constructor(private httpService: HttpService, private appService: AppConfigService) {
    this.url = this.appService.baseUrl + this.appService.apiUrl
  }

  public listAll(imei: string) {
    return this.httpService.get(this.url + this.appService.locationUrl + "/" + imei)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  listLast(imei: string) {
    return this.httpService.get(this.url + this.appService.locationUrl + "/last/" + imei)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  listAllLast() {
    return this.httpService.get(this.url + this.appService.locationUrl)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }
}
