ALTER TABLE bclient.dim_bank ALTER COLUMN bank_corr_acc DROP NOT NULL;
ALTER TABLE bclient.dim_legal_entity ALTER COLUMN legal_entity_ogrn DROP NOT NULL;

insert into bclient.dim_bank values (5, 'ГУ Банка России по ЦФО', '7702235133', '770245014', '044525000', null);

insert into bclient.dim_legal_entity values (5, 'Налоговая № 10', 'Инспекция Федеральной налоговой службы № 10 по г. Москве', '7710047253', '772601001', null, '115191 Москва, Б.Тульская ул., д. 15');

insert into bclient.dim_account values (11, 'Расчётный счёт', '40101810045250010041', 5, 5, '643', 1);

commit;
