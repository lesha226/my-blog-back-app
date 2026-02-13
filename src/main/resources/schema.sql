create table if not exists posts(
    id          bigserial       primary key,
    title       varchar         not null,
    text        varchar         not null,
    tags        varchar array   not null default array[],
    likes_count integer         not null default 0
);

create table if not exists comments(
    id          bigserial       primary key,
    post_id     bigint          not null references posts(id) on delete cascade,
    text        varchar         not null
);

create table if not exists images(
    post_id     bigint          primary key references posts(id) on delete cascade,
    body        blob            not null
);