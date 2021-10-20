import { Users} from './project.model';
import {Loan_accounts} from './project.model';
import {Repayment_schedule} from './project.model';
import {Savings_accounts} from './project.model';

describe('Users', () => {
  it('should create an instance', () => {
    expect(new Users()).toBeTruthy();
  });
});
describe('Loan_accounts', () => {
    it('should create an instance', () => {
      expect(new Loan_accounts()).toBeTruthy();
    });
  });
  describe('Savings_accounts', () => {
    it('should create an instance', () => {
      expect(new Savings_accounts()).toBeTruthy();
    });
  });
  describe('Repayment_schedule', () => {
    it('should create an instance', () => {
      expect(new Repayment_schedule()).toBeTruthy();
    });
  });