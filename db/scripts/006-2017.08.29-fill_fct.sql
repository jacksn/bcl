ALTER TABLE bclient.fct_account_balance ALTER COLUMN account_balance_amt TYPE numeric(10,2) USING account_balance_amt::numeric;
ALTER TABLE bclient.fct_operation ALTER COLUMN operation_amt TYPE numeric(10,2) USING operation_amt::numeric;
ALTER TABLE bclient.fct_payment_order ALTER COLUMN payment_order_amt TYPE numeric(10,2) USING payment_order_amt::numeric;

insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 50000, 1);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 50000, 2);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 50000, 3);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 100000, 4);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 100000, 5);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 0, 6);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 60000, 7);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 70000, 9);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-01', 500, 10);

insert into bclient.fct_payment_order (payment_order_num, payment_order_date,
  sender_legal_entity_id, sender_account_id, recipient_legal_entity_id, recipient_account_id, contract_id,
  currency_code, payment_order_amt, payment_reason, payment_priority_code, payment_order_status_id)
values (1000, date'2017-08-07', 1, 1, 3, 5, 3, '643', 1200, 'Оплата услуги Холодное водостабжение за июль 2017', '05', 5);

insert into bclient.fct_payment_order (payment_order_num, payment_order_date,
  sender_legal_entity_id, sender_account_id, recipient_legal_entity_id, recipient_account_id, contract_id,
  currency_code, payment_order_amt, payment_reason, payment_priority_code, payment_order_status_id)
values (200, date'2017-08-07', 2, 3, 3, 5, 4, '643', 800, 'Оплата услуги Холодное водостабжение за июль 2017', '05', 5);

insert into bclient.fct_payment_order (payment_order_num, payment_order_date,
  sender_legal_entity_id, sender_account_id, recipient_legal_entity_id, recipient_account_id, contract_id,
  currency_code, payment_order_amt, payment_reason, payment_priority_code, payment_order_status_id)
values (1001, date'2017-08-09', 1, 1, 2, 3, 1, '643', 2000, 'Оплата услуги Теплоснабжение за июль 2017', '05', 5);

insert into bclient.fct_payment_order (payment_order_num, payment_order_date,
  sender_legal_entity_id, sender_account_id, recipient_legal_entity_id, recipient_account_id, contract_id,
  currency_code, payment_order_amt, payment_reason, payment_priority_code, payment_order_status_id)
values (400, date'2017-08-09', 4, 7, 1, 1, 7, '643', 1500, 'Оплата поставки товара', '05', 5);

insert into bclient.fct_payment_order (payment_order_num, payment_order_date,
  sender_legal_entity_id, sender_account_id, recipient_legal_entity_id, recipient_account_id, contract_id,
  currency_code, payment_order_amt, payment_reason, payment_priority_code, payment_order_status_id)
values (100, date'2017-08-09', 3, 5, 4, 7, 9, '643', 700, 'Оплата поставки товара', '05', 5);

insert into bclient.fct_operation (operation_date, operation_amt, debet_account_id, kredit_account_id, operation_descr)
values (date'2017-08-07', 1200, 1, 5, 'Оплата услуги Холодное водостабжение за июль 2017. ПП № 1000');
insert into bclient.fct_operation (operation_date, operation_amt, debet_account_id, kredit_account_id, operation_descr)
values (date'2017-08-07', 800, 3, 5, 'Оплата услуги Холодное водостабжение за июль 2017. ПП № 200');
insert into bclient.fct_operation (operation_date, operation_amt, debet_account_id, kredit_account_id, operation_descr)
values (date'2017-08-09', 2000, 1, 3, 'Оплата услуги Теплоснабжение за июль 2017. ПП № 1001');
insert into bclient.fct_operation (operation_date, operation_amt, debet_account_id, kredit_account_id, operation_descr)
values (date'2017-08-09', 1500, 7, 1, 'Оплата поставки товара. ПП № 400');
insert into bclient.fct_operation (operation_date, operation_amt, debet_account_id, kredit_account_id, operation_descr)
values (date'2017-08-09', 700, 5, 7, 'Оплата поставки товара. ПП № 100');

insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-07', 48800, 1);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-07', 49200, 3);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-07', 102000, 5);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-09', 48300, 1);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-09', 51200, 3);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-09', 101300, 5);
insert into bclient.fct_account_balance (account_balance_date, account_balance_amt, account_id) values (date'2017-08-09', 59200, 7);

commit;
