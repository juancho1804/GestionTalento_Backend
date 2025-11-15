import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ListarActividades } from './listar-actividades';

describe('ListarActividades', () => {
  let component: ListarActividades;
  let fixture: ComponentFixture<ListarActividades>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, ListarActividades]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarActividades);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
