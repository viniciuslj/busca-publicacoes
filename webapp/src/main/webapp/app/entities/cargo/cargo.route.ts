import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cargo } from 'app/shared/model/cargo.model';
import { CargoService } from './cargo.service';
import { ICargo } from 'app/shared/model/cargo.model';

@Injectable({ providedIn: 'root' })
export class CargoResolve implements Resolve<ICargo> {
  constructor(private service: CargoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICargo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cargo>) => response.ok),
        map((cargo: HttpResponse<Cargo>) => cargo.body)
      );
    }
    return of(new Cargo());
  }
}

export const cargoRoute: Routes = [];

export const cargoPopupRoute: Routes = [];
