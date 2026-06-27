create table users (
    id bigserial primary key,
    nickname varchar(255),
    password varchar(255),
    name varchar(255),
    last_name varchar(255),
    email varchar(255),
    phone varchar(255)
);

create table posts (
    id bigserial primary key,
    user_id bigint,
    id_channel bigint,
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

create table comments (
    id bigserial primary key,
    id_post bigint,
    id_user bigint,
    content varchar(255),
    data_created timestamp(6),
    data_edited timestamp(6),
    data_deleted timestamp(6)
);

create table likes (
    id bigserial primary key,
    id_post bigint,
    id_comment bigint,
    id_user bigint
);

create table dislikes (
    id bigserial primary key,
    id_post bigint,
    id_comment bigint,
    id_user bigint
);

create table users_posts (
    user_id bigint not null,
    posts_id bigint not null unique,
    constraint fk_users_posts_user foreign key (user_id) references users (id),
    constraint fk_users_posts_post foreign key (posts_id) references posts (id)
);

create table users_channels (
    user_id bigint not null,
    channels_id bigint not null unique,
    constraint fk_users_channels_user foreign key (user_id) references users (id),
    constraint fk_users_channels_channel foreign key (channels_id) references channels (id)
);

create table channels_members (
    channel_id bigint not null,
    members_id bigint not null unique,
    constraint fk_channels_members_channel foreign key (channel_id) references channels (id),
    constraint fk_channels_members_user foreign key (members_id) references users (id)
);

create table channels_posts (
    channel_id bigint not null,
    posts_id bigint not null unique,
    constraint fk_channels_posts_channel foreign key (channel_id) references channels (id),
    constraint fk_channels_posts_post foreign key (posts_id) references posts (id)
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

create table posts_comments (
    post_id bigint not null,
    comments_id bigint not null unique,
    constraint fk_posts_comments_post foreign key (post_id) references posts (id),
    constraint fk_posts_comments_comment foreign key (comments_id) references comments (id)
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
                                follower_id bigint not null,
                                primary key (user_id, follower_id),
                                constraint fk_user_followers_user foreign key (user_id) references users (id),
                                constraint fk_user_followers_follower foreign key (follower_id) references users (id)
);
