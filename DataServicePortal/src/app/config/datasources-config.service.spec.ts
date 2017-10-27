import { TestBed, inject } from '@angular/core/testing';

import { DatasourcesConfigService } from './datasources-config.service';

describe('DatasourcesConfigService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DatasourcesConfigService]
    });
  });

  it('should be created', inject([DatasourcesConfigService], (service: DatasourcesConfigService) => {
    expect(service).toBeTruthy();
  }));
});
