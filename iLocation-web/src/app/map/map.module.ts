import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapRoutingModule } from './map-routing.module';
import { AgmCoreModule } from '@agm/core';
import { MapComponent } from './map.component';
import { MapService } from './map.service';

@NgModule({
  imports: [
    CommonModule,
    MapRoutingModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAU481L9htwQ9CFgEpQYBPibCVcaMCfuTg'
    })
  ],
  declarations: [
    MapComponent
  ],
  providers: [
    MapService
  ]
})
export class MapModule { }
