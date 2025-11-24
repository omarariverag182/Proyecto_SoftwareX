import { TestBed } from '@angular/core/testing';

import { CuentaAhorros } from './cuenta-ahorros';

describe('CuentaAhorros', () => {
  let service: CuentaAhorros;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CuentaAhorros);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
