import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentComponent } from './repayment.component';

describe('RepaymentComponent', () => {
  let component: RepaymentComponent;
  let fixture: ComponentFixture<RepaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RepaymentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
