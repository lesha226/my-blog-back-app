create table if not exists posts(
    id          bigserial primary key,
    title       varchar(256),
    text        varchar(256),
    likes_count integer default 0
);

create table if not exists comments(
    id          bigserial       primary key,
    post_id     bigint          not null,
    text        varchar(256)    not null
);
