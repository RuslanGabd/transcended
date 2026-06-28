alter table users
    add column if not exists role varchar(255) not null default 'USER',
    add column if not exists online_status varchar(255) not null default 'OFFLINE',
    add column if not exists last_seen_at timestamp(6),
    add column if not exists banned_status boolean not null default false,
    add column if not exists about varchar(255);
