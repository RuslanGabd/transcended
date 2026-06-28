alter table users
    add column role varchar(255) not null default 'USER',
    add column online_status varchar(255) not null default 'OFFLINE',
    add column last_seen_at timestamp(6),
    add column banned_status boolean not null default false,
    add column about varchar(255);
