create table users (
    id bigserial primary key,
    full_name text not null,
    phone text unique not null,
    gender text,
    category text,
    discount double precision default 0,
    specialization text,
    qualification text,
    password_hash text,
    role text not null
);

create table services (
    id bigserial primary key,
    name text not null,
    type text not null,
    hall_type text not null,
    price double precision not null
);

create table visits (
    id bigserial primary key,
    client_id bigint not null references users(id),
    master_id bigint not null references users(id),
    visit_date date not null,
    total_cost double precision not null
);

create table visit_service (
    id bigserial primary key,
    visit_id bigint not null references visits(id) on delete cascade,
    service_id bigint not null references services(id),
    price_at_time double precision not null
);

insert into users(full_name, phone, password_hash, role)
values
('Администратор', 'admin', 'admin', 'ADMIN'),
('Руководитель', 'manager', 'manager', 'MANAGER');