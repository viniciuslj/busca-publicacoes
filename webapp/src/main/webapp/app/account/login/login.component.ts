import { AccountService } from './../../core/auth/account.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { JhiEventManager } from 'ng-jhipster';
import { TokenLogin } from './token-login';
import { LoginService } from 'app/core/login/login.service';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'my-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.scss'],
  providers: [ConfirmationService]
})
export class LoginComponent implements OnInit {
  jwtHelper = new JwtHelperService();
  msgErro: string = null;
  tokenUrl: string;
  decodedToken: TokenLogin;
  authenticating: boolean;

  constructor(
    private accountService: AccountService,
    private eventManager: JhiEventManager,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    if (this.isAuthenticated()) {
      return this.processAuthenticatedUser();
    }

    this.login();
  }

  processAuthenticatedUser() {
    this.accountService.identity().then((account: any) => {
      this.confirmationService.confirm({
        key: 'logincomponent',
        message: 'Existe uma sessão aberta para o usuário:<br>' + '<strong>' + account.nome + '</strong>',
        accept: () => {
          this.loginService.logout();
          this.login();
        },
        reject: () => this.navigateToHome()
      });
    });
  }

  login() {
    this.authenticating = true;
    setTimeout(() => {
      this.activatedRoute.queryParams.subscribe(params => {
        this.tokenUrl = params['token'];
        this.decodedToken = this.validateToken(this.tokenUrl);

        if (this.decodedToken) {
          this.loginService
            .login({ token: this.tokenUrl, rememberMe: true })
            .then(() => this.processSuccess())
            .catch(response => this.processError(response));
        }
      });
    }, 2000);
  }

  validateToken(token) {
    try {
      const decodedToken = this.jwtHelper.decodeToken(token);
      if (this.jwtHelper.isTokenExpired(token)) {
        this.showErrorDetail('login.messages.error.authentication', 'login.messages.error.token.expired');
        return null;
      }

      return decodedToken;
    } catch (e) {
      this.showErrorDetail('login.messages.error.authentication', 'login.messages.error.token.invalid');
      return null;
    }
  }

  navigateToHome() {
    this.router.navigate(['']);
  }

  processSuccess() {
    this.authenticating = false;
    this.navigateToHome();
    this.eventManager.broadcast({
      name: 'authenticationSuccess',
      content: 'Sending Authentication Success'
    });
  }

  processError(response) {
    const nameMsg = response.status === 401 ? 'login.messages.error.authentication' : 'error.general';

    this.showError(nameMsg, response.error.detail);
  }

  showError(nameMsg, detail) {
    this.authenticating = false;
    this.translateService.get(nameMsg).subscribe(message => {
      this.msgErro = detail === undefined ? message : message + ' ' + detail;
    });
  }

  showErrorDetail(nameMsg, nameDetail) {
    this.translateService.get(nameDetail).subscribe(message => {
      this.showError(nameMsg, message);
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }
}
