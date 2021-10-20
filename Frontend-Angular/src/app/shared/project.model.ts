export class Users {
     username: any;
     password: any; 
      
      
    }
    
    export class Savings_accounts {
      account_no: any; 
      name: any; 
      balance: any; 
      email: any; 
      loan_exists: any; 
      
    }
    
    export class Loan_accounts
    {
       
     loan_id: any; 
     tenure: any; 
       account_no: any; 
       loan_status: any; 
       total_loan: any; 
       interest_rate: any; 
       property_img: any; 
       property_address:  any;
    }
    
    export class Repayment_schedule
    {
      id: any; 
      month_year: any;
      loan_id: any;
      emi: any;
      principal_component: any;
      interest_component: any;
      principal_outstanding: any;
      payment_status: any;
    }
    