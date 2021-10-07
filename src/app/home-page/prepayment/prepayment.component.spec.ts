import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrepaymentComponent } from './prepayment.component';

describe('PrepaymentComponent', () => {
  let component: PrepaymentComponent;
  let fixture: ComponentFixture<PrepaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrepaymentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrepaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
