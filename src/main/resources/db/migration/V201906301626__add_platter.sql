create table platter (
        id serial primary key,
        name text,
        price double precision
);

insert into platter (name, price) values ('Väike', 65.00);
insert into platter (name, price) values ('Suur', 85.00);
