insert into bclient.dim_user values (1, 'aivanov', 'Иванов Александр Дмитриевич', 'ivanov');
insert into bclient.dim_user values (2, 'apetrov', 'Петров Алексей Владимирович', 'petrov');
insert into bclient.dim_user values (3, 'asidorov', 'Сидоров Андрей Григорьевич', 'sidorov');

insert into bclient.dim_legal_entity values (1, 'Ветмедстаб', 'ООО "ВетМедСнаб"', '7714698320', '77145841', '5077746887312', '109115 Москва, Тверская-Ямская ул., д. 15 с. 2');
insert into bclient.dim_legal_entity values (2, 'Теплоэнергосбыт', 'ООО "ТеплоЭнергоСбыт"', '7714485662', '77145118', '5077642887447', '108145 Москва, Академика Янгеля ул., д. 245');
insert into bclient.dim_legal_entity values (3, 'Мосводоканал', 'ООО "МосВодоканал"', '7714485272', '77146251', '5077751884565', '110102 Москва, Ямского поля ул., д. 31');
insert into bclient.dim_legal_entity values (4, 'Экотекстиль', 'ООО "ЭкоТекстиль"', '7714752214', '77144232', '5077746655412', '121014 Москва, Полежаевская ул., д. 7 с. 5');

insert into bclient.rel_user_legal_entity values (1, 1);
insert into bclient.rel_user_legal_entity values (2, 2);
insert into bclient.rel_user_legal_entity values (2, 3);
insert into bclient.rel_user_legal_entity values (3, 4);

insert into bclient.dim_bank values (1, 'ПАО "Сбербанк"', '7707083893', '773601001', '044525225', '30101810400000000225');
insert into bclient.dim_bank values (2, 'АО "Альфа-банк"', '7728168971', '770801001', '044525593', '30101810200000000593');
insert into bclient.dim_bank values (3, 'ПАО "Банк ВТБ"', '7702070139', '783501001', '044525187', '30101810700000000187');

commit;
