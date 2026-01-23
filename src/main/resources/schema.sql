create table if not exists comments(
    id          bigserial       primary key,
    post_id     bigint          not null,
    text        varchar(256)    not null
);

insert into comments(post_id, text) values(1, 'Some comment.');