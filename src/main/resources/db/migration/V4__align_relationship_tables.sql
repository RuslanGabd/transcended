alter table users
    drop column if exists followers_id;

create table if not exists channel_members (
    user_id bigint not null,
    channel_id bigint not null,
    primary key (user_id, channel_id),
    constraint fk_channel_members_user foreign key (user_id) references users (id),
    constraint fk_channel_members_channel foreign key (channel_id) references channels (id)
);

do $$
begin
    if to_regclass('users_channels') is not null then
        insert into channel_members (user_id, channel_id)
        select user_id, channels_id
        from users_channels
        on conflict do nothing;
    end if;

    if to_regclass('channels_members') is not null then
        insert into channel_members (user_id, channel_id)
        select members_id, channel_id
        from channels_members
        on conflict do nothing;
    end if;

    if to_regclass('users_posts') is not null then
        update posts
        set user_id = users_posts.user_id
        from users_posts
        where posts.id = users_posts.posts_id
          and posts.user_id is null;
    end if;

    if to_regclass('channels_posts') is not null then
        update posts
        set channel_id = channels_posts.channel_id
        from channels_posts
        where posts.id = channels_posts.posts_id
          and posts.channel_id is null;
    end if;

    if to_regclass('posts_comments') is not null then
        update comments
        set post_id = posts_comments.post_id
        from posts_comments
        where comments.id = posts_comments.comments_id
          and comments.post_id is null;
    end if;
end $$;

drop table if exists users_posts;
drop table if exists users_channels;
drop table if exists channels_members;
drop table if exists channels_posts;
drop table if exists posts_comments;

do $$
begin
    if not exists (
        select 1
        from pg_constraint
        where conname = 'fk_posts_channel'
    ) then
        alter table posts
            add constraint fk_posts_channel foreign key (channel_id) references channels (id);
    end if;
end $$;
