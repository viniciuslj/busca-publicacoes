import { Router } from '@angular/router';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AccountService, Account } from 'app/core';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, AfterViewInit {
  account: Account;
  modalRef: NgbModalRef;

  constructor(private accountService: AccountService, private eventManager: JhiEventManager, private router: Router) {}

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.account = account;

      if (this.isAuthenticated()) {
        this.router.navigate(['pagina']);
      } else {
        window.location.href = 'https://portal.pm.es.gov.br';
      }
    });
    this.registerAuthenticationSuccess();
  }

  ngAfterViewInit(): void {}

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
        this.router.navigate(['pagina']);
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }
}
