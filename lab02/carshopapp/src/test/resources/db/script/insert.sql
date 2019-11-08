-- Добавление записей в таблицу Owner
insert into Owner
    (Name, LastName, Email, Phone, Birthday)
values
    ('Игорь', 'Марсианин', 'ilovemars@test.ru', '+79209999999', '1972-03-11'),
    ('Владимир', 'Португалец', 'visitportugal@test.ru', '+79109999999', '1999-10-27')
;
-- Добавление записей в таблицу Car
insert into Car
    (Brand, Model, Color, Year, Mileage, Owner, Price)
values
    ('Jeep', 'Rubicon', 'Black Raptor', '2018-01-15', 10000, 2, 100000000000),
    ('ВАЗ', '2114', 'Серебристый', '2004-05-30', 100000, 2, 150000),
    ('ГАЗ', '2752', 'Буран', '2005-06-19', 300000, 1, 400000)
;