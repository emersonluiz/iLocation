import { Component, OnInit } from '@angular/core';
import { MapService } from './map.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  lat: number = -23.9618;
  lng: number = -46.3322;

  lat1: number = -23.8618;
  lng1: number = -46.4322;

  markers = [];

  yellow = "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png";
  green = "http://maps.google.com/mapfiles/ms/icons/green-dot.png";
  red = "http://maps.google.com/mapfiles/ms/icons/red-dot.png";

  history = false;

  //http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|FE7569
  

  constructor(private mapService: MapService) { }

  ngOnInit() {
    this.mapService.listAllLast().subscribe(
      rtn => {
        console.log(rtn)
        this.markers = rtn;
      }
    )
  }

  onHistory(imei: string) {
    this.history = true;
    this.markers = [];
    this.mapService.listAll(imei).subscribe(
      rtn => {
        this.markers = rtn;
      }
    )
  }

  onBack() {
    this.history = false;
    this.markers = [];
    this.mapService.listAllLast().subscribe(
      rtn => {
        this.markers = rtn;
      }
    )
  }

  ver(vl) {
    console.log(vl)
  }
}
