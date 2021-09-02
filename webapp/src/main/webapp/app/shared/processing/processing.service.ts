import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProcessingService {
  private subject = new BehaviorSubject(false);
  observable = this.subject.asObservable();

  show() {
    this.subject.next(true);
  }

  hide() {
    this.subject.next(false);
  }

  isProcessing() {
    return this.subject.getValue();
  }
}
