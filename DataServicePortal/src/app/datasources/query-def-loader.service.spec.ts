import { TestBed, inject } from '@angular/core/testing';

import { QueryDefLoaderService } from './query-def-loader.service';

describe('QueryTypeDefLoaderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [QueryDefLoaderService]
    });
  });

  it('should be created', inject([QueryDefLoaderService], (service: QueryDefLoaderService) => {
    expect(service).toBeTruthy();
  }));
});
