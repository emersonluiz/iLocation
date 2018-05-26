import { Injectable } from '@angular/core';

@Injectable()
export class AppConfigService {

  constructor() { }

  private privateBaseUrl: string = 'http://localhost';
  private privateApiUrl: string = ':9090/iLocation-service';
  private privateLocationUrl: string = '/locations';

  public get baseUrl(): string {
    return this.privateBaseUrl
  }

  public get apiUrl(): string {
      return this.privateApiUrl
  }

  public get locationUrl() {
    return this.privateLocationUrl;
  }

}
