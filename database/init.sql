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
    category text not null,
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

insert into services(name, category, type, hall_type, price)
values
('Каскад', 'WOMEN_HAIRCUT', 'HAIRCUT', 'FEMALE_HALL', 1500),
('Каре', 'WOMEN_HAIRCUT', 'HAIRCUT', 'FEMALE_HALL', 1400),
('Лесенка', 'WOMEN_HAIRCUT', 'HAIRCUT', 'FEMALE_HALL', 1300),
('Женская стрижка', 'WOMEN_HAIRCUT', 'HAIRCUT', 'FEMALE_HALL', 1200),

('Fade', 'MEN_HAIRCUT', 'HAIRCUT', 'MALE_HALL', 1200),
('Полубокс', 'MEN_HAIRCUT', 'HAIRCUT', 'MALE_HALL', 900),
('Мужская стрижка', 'MEN_HAIRCUT', 'HAIRCUT', 'MALE_HALL', 1000),
('Оформление бороды', 'MEN_HAIRCUT', 'HAIRCUT', 'MALE_HALL', 700),

('Airtouch', 'WOMEN_COLORING', 'COLORING', 'FEMALE_HALL', 5000),
('Мелирование', 'WOMEN_COLORING', 'COLORING', 'FEMALE_HALL', 3500),
('Тонирование', 'WOMEN_COLORING', 'COLORING', 'FEMALE_HALL', 2500),

('Мужское окрашивание', 'MEN_COLORING', 'COLORING', 'MALE_HALL', 1800),
('Камуфляж седины', 'MEN_COLORING', 'COLORING', 'MALE_HALL', 1300),

('Маникюр', 'NAIL_SERVICE', 'NAIL', 'FEMALE_HALL', 1000),
('Покрытие гель-лаком', 'NAIL_SERVICE', 'NAIL', 'FEMALE_HALL', 1500),
('Наращивание ногтей', 'NAIL_SERVICE', 'NAIL', 'FEMALE_HALL', 2500),
('Педикюр', 'NAIL_SERVICE', 'NAIL', 'FEMALE_HALL', 1800),

('Коррекция бровей', 'BROWS_LASHES', 'BROWS', 'FEMALE_HALL', 700),
('Окрашивание бровей', 'BROWS_LASHES', 'BROWS', 'FEMALE_HALL', 800),
('Ламинирование ресниц', 'BROWS_LASHES', 'LASHES', 'FEMALE_HALL', 1800),

('Укладка', 'STYLING', 'STYLING', 'FEMALE_HALL', 1500),
('Вечерняя укладка', 'STYLING', 'STYLING', 'FEMALE_HALL', 2500);