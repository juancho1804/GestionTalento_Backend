import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { EncargadaDashboard } from './encargada-dashboard';

describe('EncargadaDashboard', () => {
  let component: EncargadaDashboard;
  let fixture: ComponentFixture<EncargadaDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, EncargadaDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EncargadaDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
