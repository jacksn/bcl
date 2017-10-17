insert into bclient.dim_account_status values (1, 'ACTIVE', 'Активный');
insert into bclient.dim_account_status values (2, 'LOCKED', 'Заблокирован');
insert into bclient.dim_account_status values (3, 'CLOSED', 'Закрыт');

insert into bclient.dim_payment_order_status values (1, 'NEW', 'Новое');
insert into bclient.dim_payment_order_status values (2, 'ACCEPTED', 'Принято');
insert into bclient.dim_payment_order_status values (3, 'CANCELLED', 'Отменено');
insert into bclient.dim_payment_order_status values (4, 'IN_PROGRESS', 'В обработке');
insert into bclient.dim_payment_order_status values (5, 'EXECUTED', 'Исполнено');
insert into bclient.dim_payment_order_status values (6, 'REJECTED', 'Отклонено');

commit;
