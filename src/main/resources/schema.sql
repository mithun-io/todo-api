create table users
(
    id         bigserial primary key,
    username   varchar(255)        not null,
    email      varchar(255) unique not null,
    password   varchar(255)        not null,
    created_at timestamp
);