import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'rgMask' })
export class RgMaskPipe implements PipeTransform {
  transform(v) {
    return v !== null ? this.mask(v) : v;
  }

  mask(v) {
    const rg = Number(v) / 10;
    return rg
      .toFixed(1)
      .replace(/\d(?=(\d{3})+\.)/g, '$&.')
      .replace(/\.(\d)$/, '-$1');
  }
}
