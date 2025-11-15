import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { DetallesActividadIncentivos } from './detalles-actividad';

describe('DetallesActividadIncentivos', () => {
  let component: DetallesActividadIncentivos;
  let fixture: ComponentFixture<DetallesActividadIncentivos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, DetallesActividadIncentivos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetallesActividadIncentivos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
