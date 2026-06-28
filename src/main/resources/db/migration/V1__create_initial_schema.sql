create table users (
    id bigserial primary key,
    nickname varchar(255),
    password varchar(255),
    name varchar(255),
    last_name varchar(255),
    email varchar(255),
    phone varchar(255),
    role varchar(255) not null default 'USER',
    online_status varchar(255) not null default 'OFFLINE',
    last_seen_at timestamp(6),
    banned_status boolean not null default false,
    about varchar(255)
);

create table posts (
    id bigserial primary key,
    user_id bigint,
    channel_id bigint,
    title varchar(255),
    content varchar(255),
    data_created timestamp(6),
    data_edited timestamp(6),
    data_deleted timestamp(6),
    constraint fk_posts_user foreign key (user_id) references users (id)
);

create table channels (
    id bigserial primary key,
    name varchar(255),
    description varchar(255),
    admin_id bigint,
    constraint fk_channels_admin foreign key (admin_id) references users (id)
);

alter table posts
    add constraint fk_posts_channel foreign key (channel_id) references channels (id);

create table comments (
    id bigserial primary key,
    post_id bigint,
    user_id bigint,
    content varchar(255),
    data_created timestamp(6),
    data_edited timestamp(6),
    data_deleted timestamp(6)
);

create table likes (
    id bigserial primary key,
    post_id bigint,
    comment_id bigint,
    user_id bigint
);

create table dislikes (
    id bigserial primary key,
    post_id bigint,
    comment_id bigint,
    user_id bigint
);

create table channel_members (
    user_id bigint not null,
    channel_id bigint not null,
    primary key (user_id, channel_id),
    constraint fk_channel_members_user foreign key (user_id) references users (id),
    constraint fk_channel_members_channel foreign key (channel_id) references channels (id)
);

create table posts_likes (
    post_id bigint not null,
    likes_id bigint not null unique,
    constraint fk_posts_likes_post foreign key (post_id) references posts (id),
    constraint fk_posts_likes_like foreign key (likes_id) references likes (id)
);

create table posts_dislikes (
    post_id bigint not null,
    dislikes_id bigint not null unique,
    constraint fk_posts_dislikes_post foreign key (post_id) references posts (id),
    constraint fk_posts_dislikes_dislike foreign key (dislikes_id) references dislikes (id)
);

create table comments_likes (
    comment_id bigint not null,
    likes_id bigint not null unique,
    constraint fk_comments_likes_comment foreign key (comment_id) references comments (id),
    constraint fk_comments_likes_like foreign key (likes_id) references likes (id)
);

create table comments_dislikes (
    comment_id bigint not null,
    dislikes_id bigint not null unique,
    constraint fk_comments_dislikes_comment foreign key (comment_id) references comments (id),
    constraint fk_comments_dislikes_dislike foreign key (dislikes_id) references dislikes (id)
);

create table user_followers (
                                user_id bigint not null,
                                followers_id bigint not null,
                                primary key (user_id, followers_id),
                                constraint fk_user_followers_user foreign key (user_id) references users (id),
                                constraint fk_user_followers_follower foreign key (followers_id) references users (id)
);
