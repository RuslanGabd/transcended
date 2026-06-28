create table profiles (
    id bigserial primary key,
    user_id bigint not null unique,
    display_name varchar(80),
    bio varchar(500),
    avatar_url varchar(255),
    location varchar(255),
    website_url varchar(255),
    created_at timestamp(6),
    updated_at timestamp(6),
    constraint fk_profiles_user foreign key (user_id) references users (id)
);
