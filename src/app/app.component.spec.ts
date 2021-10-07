import { RepaymentComponent } from './home-page/repayment/repayment.component';
import { PrepaymentComponent } from './home-page/prepayment/prepayment.component';
import { LoanDetailsComponent } from './home-page/loan-details/loan-details.component';
import { ApplyLoanComponent } from './home-page/apply-loan/apply-loan.component';
import { HomePageComponent } from './home-page/home-page.component';

import { LoginPageComponent } from './login-page/login-page.component';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { SavingAccountComponent } from './home-page/saving-account/saving-account.component';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent,
        LoginPageComponent,
        HomePageComponent, 
        ApplyLoanComponent,
        LoanDetailsComponent,
        PrepaymentComponent,
        RepaymentComponent,
        SavingAccountComponent
        
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'HomeLoan'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('HomeLoan');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain('HomeLoan app is running!');
  });
});
