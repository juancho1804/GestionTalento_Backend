import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarParticipantes } from './listar-participantes';

describe('ListarParticipantes', () => {
  let component: ListarParticipantes;
  let fixture: ComponentFixture<ListarParticipantes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarParticipantes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarParticipantes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
