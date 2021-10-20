create database Test2;
use test2;
-- ==============================================================================================================
-- users table
create table users(
	username varchar(50) primary key,
    password varchar(50) not null
);

insert into users(username, password) values ('pranjalshinde.1729@gmail.com','abc123');
insert into users(username, password) values ('hrushikesh1857@gmail.com','abc123');
insert into users(username, password) values ('dishavkhandelwal@gmail.com','abc123');
select * from users;

-- ==============================================================================================================
-- savings accounts table
create table savings_accounts(
	account_no int primary key,
    name varchar(25) not null,
    balance float not null,
    email varchar(50) not null,
    foreign key (email) references users(username),
    loan_exists boolean not null
);

insert into savings_accounts(account_no, name, balance, email, loan_exists) values 
	(1254764,'Pranjal Shinde', 2600000, 'pranjalshinde.1729@gmail.com', false);
    
insert into savings_accounts(account_no, name, balance, email, loan_exists) values 
	(1254765,'Hrushikesh Salunkhe', 4800000, 'hrushikesh1857@gmail.com', false);
    
insert into savings_accounts(account_no, name, balance, email, loan_exists) values 
	(1254766,'Disha Khandelwal', 1600000, 'dishavkhandelwal@gmail.com', false);
select * from savings_accounts;

-- ==============================================================================================================
-- loan accounts
create table loan_accounts(
	loan_id int primary key auto_increment,
    tenure int(2) not null check(tenure >4 and tenure <13),
    account_no int not null,
	-- foreign key (account_no) references savings_accounts(account_no),
    loan_status varchar(10) not null,
    total_loan float not null,
    interest_rate float not null,
    property_img  LONGBLOB,
    property_address varchar(100)
);
alter table loan_accounts auto_increment=80001;
insert into loan_accounts(tenure, account_no, loan_status, total_loan, interest_rate,property_img, property_address)
	values(9, 1254764, 'Approved',  500000, 12, null, "nfklenflk");
select * from loan_accounts;


-- delete from loan_accounts where account_no = 1254764;
    
-- ==============================================================================================================
-- loan repayment schedule
create table repayment_schedule(
	id int auto_increment primary key,
	month_year varchar(20) not null,
    loan_id int not null,
    -- foreign key(loan_id) references loan_accounts(loan_id),
    EMI float not null,
    principal_component float not null,
    interest_component float not null,
    principal_outstanding float not null,
    payment_status varchar(20)
    -- primary key(month_year,loan_id)    
);
-- drop table repayment_schedule

-- ==============================================================================================================
-- ==============================================================================================================4
select * from savings_accounts;
select * from loan_accounts;
select * from repayment_schedule;

-- ==============================================================================================================
-- ==============================================================================================================

-- ==============================================================================================================
-- trigger to update loan_exists status in savings_accounts table
delimiter #
create trigger change_loan_exists_status after insert on loan_accounts
   for each row 
   begin
		select account_no into @acc_id from loan_accounts where loan_id = (select last_insert_id());
		update savings_accounts set loan_exists = true where account_no = @acc_id;
   end#
-- drop trigger change_loan_exists_status;
-- insert into loan_accounts(tenure, account_no, loan_status, total_loan, interest_rate,property_img, property_address)
-- 	values(10, 1254745, 'Approved',  500000, 7, null, null);
-- select * from loan_accounts;
-- select * from savings_accounts;
-- ==============================================================================================================

-- procedure to create records in repayment_table
-- drop procedure insert_repayment_from_loan
DELIMITER $$
CREATE PROCEDURE insert_repayment_from_loan
(
	IN loan_id int, 
    IN total_loan float,
    IN interest_rate float,
    IN tenure int
)
    BEGIN
		DECLARE counter INTEGER;
		DECLARE N INTEGER;
		DECLARE insert_EMI float ;
        DECLARE EMI float ;
        DECLARE rate float;
        DECLARE principal_component float; 
        DECLARE interest_component float;
        DECLARE principal_outstanding float;
        DECLARE payment_status varchar(15) DEFAULT 'Pending';
        
        SET N = 12 * tenure;
        SET rate = round(interest_rate / 100 / 12, 2);

        -- EMI = [P x R x (1+R)^N]/[(1+R)^N-1] 
        SET EMI = (total_loan * rate * POWER(1+rate, N)) / (POWER(1+rate, N) - 1);
        SET principal_outstanding = total_loan;
		SET counter = 1;
			WHILE counter < (N+1) DO
                
                if principal_outstanding > EMI then
					SET insert_EMI = EMI;
                    SET interest_component = principal_outstanding * rate;
					SET principal_component = EMI - interest_component;
					SET principal_outstanding = principal_outstanding - principal_component;
				else 
					SET interest_component = principal_outstanding * rate;
					SET principal_component = principal_outstanding - interest_component;
					SET insert_EMI = principal_outstanding;
					SET principal_outstanding = 0;
				end if;
                insert into repayment_schedule 
             (month_year, loan_id, EMI, principal_component, interest_component,principal_outstanding, payment_status) 
             values(concat(monthname(LAST_DAY(NOW()) + INTERVAL counter*30 DAY), " ", year(LAST_DAY(NOW()) + INTERVAL counter*30 DAY)), 
             loan_id, insert_EMI, principal_component, interest_component, principal_outstanding, 'Pending');

                SET counter = counter + 1;
            END WHILE;
    END$$
    
select loan_id into @loan_id from loan_accounts where loan_id = (select last_insert_id()) LIMIT 1;
select tenure into @tenure from loan_accounts where loan_id = @loan_id LIMIT 1;
select interest_rate into @rate from loan_accounts where loan_id = (select last_insert_id()) LIMIT 1;
select total_loan into @total_loan from loan_accounts where loan_id = (select last_insert_id()) LIMIT 1;

call insert_repayment_from_loan(@loan_id, @total_loan, @rate, @tenure);
 -- drop procedure insert_repayment_from_loan;
 -- select * from repayment_schedule;
-- ==============================================================================================================

-- procedure to auto deduct balance EMI from savings accounts and change loan status

-- DROP PROCEDURE auto_deduct_savings_account;
DELIMITER $$
CREATE PROCEDURE auto_deduct_savings_account
(
	IN in_loan_id int,
    IN EMI float,
    IN in_account_no int,
    IN tenure int,
    IN counter int
)
    BEGIN
        update loan_accounts set loan_status = 'Ongoing' where loan_id = in_loan_id;
		update savings_accounts set balance = balance - EMI where account_no = in_account_no;
		update repayment_schedule set payment_status = 'Paid' where id = counter;
    END$$
    

select tenure into @tenure from loan_accounts where loan_id = 80001 LIMIT 1;


CREATE EVENT test_event_03
ON SCHEDULE EVERY 20 SECOND
STARTS CURRENT_TIMESTAMP
ENDS CURRENT_TIMESTAMP + INTERVAL 1 MINUTE
DO call auto_deduct_savings_account(80001, 
(select EMI from repayment_schedule where loan_id = 80001 LIMIT 1),
(select account_no from loan_accounts where loan_id = 80001 LIMIT 1), 
@tenure, 
(SELECT id from repayment_schedule where loan_id = 80001 and payment_status = 'Pending' order by id asc LIMIT 1));

-- truncate table repayment_schedule ;
select * from repayment_schedule;

-- drop event test_event_03;
-- drop procedure auto_deduct_savings_account;
select * from savings_accounts;

-- ==============================================================================================================
-- ==============================================================================================================
-- Prepayment

-- SET @counter = (SELECT id from repayment_schedule where loan_id = 80002 order by id asc LIMIT 1);

-- drop procedure prepayment_foreclosure;
DELIMITER $$
CREATE PROCEDURE prepayment_foreclosure
(
	IN in_loan_id int,
    IN amount float,
    IN in_account_no int,
	IN counter int,
	IN interest_rate float,
    IN tenure int
)
    BEGIN
		DECLARE c INTEGER;
        DECLARE l INTEGER;
		DECLARE N INTEGER;
		DECLARE new_EMI float ;
        DECLARE rate float;
        DECLARE principal_comp float; 
        DECLARE interest_comp float;
        DECLARE principal_out float;

		SET N = (12 * tenure) - (SELECT COUNT(id) from repayment_schedule where loan_id = in_loan_id and payment_status = 'Paid') - 1;
		SET rate = round(interest_rate / 100 / 12, 2);
    
		update repayment_schedule set payment_status = 'Paid' where id = counter;
		update savings_accounts set balance = balance - amount where account_no = in_account_no;
        
        SET principal_out = (SELECT principal_outstanding from repayment_schedule where id = counter-1);
        
        
        if (principal_out = amount or amount > principal_out) then
			SET interest_comp = principal_out * rate;
			update repayment_schedule set interest_component = interest_comp where id = counter;
			
			SET principal_comp = principal_out - interest_comp;
			update repayment_schedule set principal_component = principal_comp where id = counter;
            
            update repayment_schedule set EMI = principal_out where id = counter;
            
			SET principal_out = 0;
			update loan_accounts set loan_status = 'Closed' where loan_id = in_loan_id;
			update repayment_schedule set principal_outstanding = principal_out where id = counter;
			
            SET c = counter+1;
            SET l = (SELECT id from repayment_schedule where loan_id = in_loan_id and payment_status = 'Pending' order by id desc LIMIT 1);
				WHILE c < (l+1) DO

					update repayment_schedule set interest_component = 0 where id = c;
					update repayment_schedule set principal_component = 0 where id = c;
					update repayment_schedule set principal_outstanding = 0 where id = c;
                    update repayment_schedule set EMI = 0 where id = c;
                    update repayment_schedule set payment_status = 'Cancelled' where id = c;
                
					SET c = c + 1;

				END WHILE;
        
        
        
		elseif principal_out > amount then
			SET interest_comp = principal_out * rate;
			update repayment_schedule set interest_component = interest_comp where id = counter;
			
			SET principal_comp= amount - interest_comp;
			update repayment_schedule set principal_component = principal_comp where id = counter;
        
        
			SET principal_out = principal_out - principal_comp;
			update loan_accounts set loan_status = 'Ongoing' where loan_id = in_loan_id;
            update repayment_schedule set principal_outstanding = principal_out where id = counter;
			update repayment_schedule set EMI = amount where id = counter;
            
			-- EMI Recalculation

			-- EMI = [P x R x (1+R)^N]/[(1+R)^N-1] 
			SET new_EMI = (principal_out * rate * POWER(1+rate, N)) / (POWER(1+rate, N) - 1);
			-- SET principal_outstanding = total_loan;
			-- SET c = (SELECT id from repayment_schedule where loan_id = 80002 and payment_status = 'Pending' order by id asc LIMIT 1);
            SET c = counter+1;
            SET l = (SELECT id from repayment_schedule where loan_id = in_loan_id and payment_status = 'Pending' order by id desc LIMIT 1);
				WHILE c < (l+1) DO
					
					if principal_out > new_EMI then
						SET interest_comp = principal_out * rate;
						update repayment_schedule set interest_component = interest_comp where id = c;
						
						SET principal_comp = new_EMI - interest_comp;
						update repayment_schedule set principal_component = principal_comp where id = c;
                        
						SET principal_out = principal_out - principal_comp;
                        update repayment_schedule set EMI = new_EMI where id = c;
					else 
						SET interest_comp = principal_out * rate;
						update repayment_schedule set interest_component = interest_comp where id = c;
						
						SET principal_comp = principal_out - interest_comp;
						update repayment_schedule set principal_component = principal_comp where id = c;
                        
						update repayment_schedule set EMI = principal_out where id = c;
						SET principal_out = 0;
						update loan_accounts set loan_status = 'Closed' where loan_id = in_loan_id;
					end if;
                    
					update repayment_schedule set principal_outstanding = principal_out where id = c;
                    
					SET c = c + 1;

				END WHILE;
	
		else 
			SET interest_comp = principal_out * rate;
			update repayment_schedule set interest_component = interest_comp where id = counter;
			
			SET principal_comp= principal_out - interest_comp;
			update repayment_schedule set principal_component = principal_comp where id = counter;
        
			update repayment_schedule set EMI = principal_out where id = counter;
			SET principal_out = 0;
			update loan_accounts set loan_status = 'Closed' where loan_id = in_loan_id;
            update repayment_schedule set principal_outstanding = principal_out where id = counter;
		end if;
        
    END$$
    
call prepayment_foreclosure(80001, 150000, (select account_no from loan_accounts where loan_id = 80001 LIMIT 1),
(SELECT id from repayment_schedule where loan_id = 80001 and payment_status = 'Pending' order by id asc LIMIT 1),
(select interest_rate from loan_accounts where loan_id = 80001 LIMIT 1),
(select tenure from loan_accounts where loan_id = 80001 LIMIT 1));

select * from savings_accounts;
select * from loan_accounts;
select * from repayment_schedule;
-- truncate table savings_accounts;
-- truncate table loan_accounts;
-- truncate table repayment_schedule;
-- ==============================================================================================================
-- ==============================================================================================================
-- select EMI into @EMI from repayment_schedule where loan_id = 80002 LIMIT 1;
-- select account_no into @account_no from loan_accounts where loan_id = 80002 LIMIT 1;
-- select @account_no ;
-- call auto_deduct_savings_account(80001, @EMI, @account_no, @tenure);
-- SET SQL_SAFE_UPDATES = 0;
-- update repayment_schedule set payment_status = 'Pending';
-- SET @counter = (SELECT id from repayment_schedule where loan_id = 80001 and payment_status = 'Pending' order by id asc LIMIT 1);
-- select @counter;
-- SELECT principal_outstanding from repayment_schedule where id = 22-1 ; -- 439761
-- ==============================================================================================================
-- ==============================================================================================================




-- DELIMITER $$
-- CREATE PROCEDURE tp
-- (
--     IN ct int
-- )
--     BEGIN
-- 		declare cr int;
--         
--         set cr = 1;
--         WHILE cr < ct+1 DO
-- 	update repayment_schedule set month_year = concat(monthname(LAST_DAY(NOW()) + INTERVAL cr*30 DAY), " ", year(LAST_DAY(NOW()) + INTERVAL cr*30 DAY)) where id = cr ;
-- 	SET cr = cr +1 ;
-- END WHILE;
--     END$$
--     
--     drop procedure tp;
--     
-- call tp(108); 

-- CREATE EVENT test_event_03
-- ON SCHEDULE EVERY 1 MINUTE
-- STARTS CURRENT_TIMESTAMP
-- ENDS CURRENT_TIMESTAMP + INTERVAL 1 MINUTE
-- DO call auto_deduct_savings_account(80001, 
-- (select EMI from repayment_schedule where loan_id = 80001 LIMIT 1),
-- (select account_no from loan_accounts where loan_id = 80001 LIMIT 1), 
-- @tenure, 
-- (SELECT id from repayment_schedule where loan_id = 80001 and payment_status = 'Pending' order by id asc LIMIT 1));