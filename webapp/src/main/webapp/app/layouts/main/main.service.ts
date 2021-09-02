import { JhiMainComponent } from './main.component';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MainService {
  mainComponent: JhiMainComponent;

  constructor() {}

  setComponent(mainComponent: JhiMainComponent) {
    this.mainComponent = mainComponent;
  }

  hideNavbar() {
    this.mainComponent.hideNavbar();
  }

  showNavbar() {
    this.mainComponent.showNavbar();
  }
}
