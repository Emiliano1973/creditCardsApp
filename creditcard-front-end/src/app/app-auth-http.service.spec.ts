import { TestBed, inject } from '@angular/core/testing';

import { AppAuthHttpService } from './app-auth-http.service';

describe('AppAuthHttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AppAuthHttpService]
    });
  });

  it('should be created', inject([AppAuthHttpService], (service: AppAuthHttpService) => {
    expect(service).toBeTruthy();
  }));
});
