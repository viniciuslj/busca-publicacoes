import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'loginMask' })
export class LoginMaskPipe implements PipeTransform {
  transform(v) {
    return v !== null
      ? v
          .toString()
          .padStart(11, '0')
          .replace(/(\d{3})(\d{3})(\d{3})(\d{2})/g, '$1.$2.$3-$4')
      : v;
  }
}
