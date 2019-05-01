create table orders (
        id serial primary key,
        price double precision,
        name text,
        date timestamp without time zone,
        created timestamp without time zone
)