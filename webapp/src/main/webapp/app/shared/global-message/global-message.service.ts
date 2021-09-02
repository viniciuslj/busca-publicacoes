import { Injectable } from '@angular/core';
import { GlobalMessageComponent } from './global-message.component';

const DEFAULT_DURATION = 7000;

@Injectable({
  providedIn: 'root'
})
export class GlobalMessageService {
  private messageComponent: GlobalMessageComponent;

  constructor() {}

  setComponent(messageComponent: GlobalMessageComponent) {
    this.messageComponent = messageComponent;
  }

  show(type, title, text, duration) {
    this.messageComponent.show(type, title, text, duration);
  }

  success(title, text = '', duration = DEFAULT_DURATION) {
    this.show('success', title, text, duration);
  }

  info(title, text = '', duration = DEFAULT_DURATION) {
    this.show('info', title, text, duration);
  }

  warn(title, text = '', duration = DEFAULT_DURATION) {
    this.show('warn', title, text, duration);
  }

  error(title, text = '', duration = DEFAULT_DURATION) {
    this.show('error', title, text, duration);
  }
}
