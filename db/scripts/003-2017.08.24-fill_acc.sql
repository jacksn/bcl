insert into bclient.dim_account values (1, 'Расчётный счёт', '40702810145460034560', 1, 1, '643', 1);
insert into bclient.dim_account values (2, 'Расчётный счёт', '40702810045500058455', 1, 1, '643', 1);
insert into bclient.dim_account values (3, 'Расчётный счёт', '40702810145410054511', 2, 1, '643', 1);
insert into bclient.dim_account values (4, 'Расчётный счёт', '40702810215040001615', 2, 2, '643', 1);
insert into bclient.dim_account values (5, 'Расчётный счёт', '40702810345410041545', 3, 1, '643', 1);
insert into bclient.dim_account values (6, 'Расчётный счёт', '40702810432140002151', 3, 3, '643', 1);
insert into bclient.dim_account values (7, 'Расчётный счёт', '40702810545580000151', 4, 1, '643', 1);
insert into bclient.dim_account values (8, 'Расчётный счёт', '40702810615040001551', 4, 2, '643', 1);
insert into bclient.dim_account values (9, 'Расчётный счёт', '40702810732070001515', 4, 3, '643', 1);
insert into bclient.dim_account values (10, 'Расчётный счёт', '40702840432140018425', 3, 3, '840', 1);

insert into bclient.dim_contract values (1, 'Договор оказания услуг', '15845', date'2017-01-01', date'2017-12-31', 2, 1, '643');
insert into bclient.dim_contract values (2, 'Договор оказания услуг', '16854', date'2017-01-01', date'2017-12-31', 2, 3, '643');
insert into bclient.dim_contract values (3, 'Договор оказания услуг', '842', date'2017-01-01', date'2017-12-31', 3, 1, '643');
insert into bclient.dim_contract values (4, 'Договор оказания услуг', '843', date'2017-01-01', date'2017-12-31', 3, 2, '643');
insert into bclient.dim_contract values (5, 'Договор оказания услуг', '872', date'2017-01-01', date'2017-12-31', 3, 4, '643');
insert into bclient.dim_contract values (6, 'Договор поставки товаров', '85/12', date'2017-01-01', date'2017-12-31', 1, 2, '643');
insert into bclient.dim_contract values (7, 'Договор поставки товаров', '45/9', date'2017-01-01', date'2017-12-31', 1, 4, '643');
insert into bclient.dim_contract values (8, 'Договор поставки товаров', '002518', date'2017-01-01', date'2017-12-31', 4, 2, '643');
insert into bclient.dim_contract values (9, 'Договор поставки товаров', '002521', date'2017-01-01', date'2017-12-31', 4, 3, '643');

commit;
