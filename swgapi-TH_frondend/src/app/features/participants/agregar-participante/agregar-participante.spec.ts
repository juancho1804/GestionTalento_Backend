import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarParticipante } from './agregar-participante';

describe('AgregarParticipante', () => {
  let component: AgregarParticipante;
  let fixture: ComponentFixture<AgregarParticipante>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgregarParticipante]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgregarParticipante);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
