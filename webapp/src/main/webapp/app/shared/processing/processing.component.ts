import { Component, OnInit } from '@angular/core';
import { ProcessingService } from './processing.service';

@Component({
  selector: 'my-processing',
  template: `
    <p-blockUI [blocked]="processing" baseZIndex="99999">
      <div class="content">
        <div class="la-ball-pulse-sync la-2x">
          <div></div>
          <div></div>
          <div></div>
        </div>
      </div>
    </p-blockUI>
  `,
  styleUrls: ['./processing.component.scss']
})
export class ProcessingComponent implements OnInit {
  processing: boolean;
  constructor(private processingService: ProcessingService) {}

  ngOnInit() {
    this.processingService.observable.subscribe(status => (this.processing = status));
  }
}
