import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { GlobalMessageService } from './global-message.service';

@Component({
  selector: 'my-global-message',
  templateUrl: './global-message.component.html',
  providers: [MessageService]
})
export class GlobalMessageComponent implements OnInit {
  constructor(private vendorMessageService: MessageService, private messageService: GlobalMessageService) {
    messageService.setComponent(this);
  }

  ngOnInit() {}

  show(type, title, text, duration) {
    this.vendorMessageService.add({
      severity: type,
      summary: title,
      detail: text,
      life: duration,
      sticky: duration <= 0
    });
  }
}
