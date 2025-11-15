import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ModalConfigPresupuesto } from './modal-config-presupuesto';

describe('ModalConfigPresupuesto', () => {
  let component: ModalConfigPresupuesto;
  let fixture: ComponentFixture<ModalConfigPresupuesto>;

  const matDialogRefStub = { close: () => {} };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalConfigPresupuesto],
      providers: [
        { provide: MatDialogRef, useValue: matDialogRefStub },
        { provide: MAT_DIALOG_DATA, useValue: {} }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalConfigPresupuesto);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
