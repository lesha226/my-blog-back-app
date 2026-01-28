create table if not exists posts(
    id          bigserial primary key,
    title       varchar(256)    not null,
    text        varchar(256)    not null,
    likes_count integer         not null default 0
);

create table if not exists comments(
    id          bigserial       primary key,
    post_id     bigint          not null,
    text        varchar(256)    not null
);

create table if not exists images(
    post_id     bigint          primary key,
    body        blob            not null
);


insert into posts(title, text, likes_count)
values
    ('Title #1', 'Some post text 1.', 0),
    ('Title #2', 'Some post text 2.', 20),
    ('Title #3', 'Some post text 3.', 30),
    ('Title #4', 'Some post text 4.', 40),
    ('Title #5', 'Some post text 5.', 50),
    ('Title #6', 'Some post text 6.', 60),
    ('Title #7', 'Some post text 7.', 70),
    ('Title #8', 'Some post text 8.', 80),
    ('Title #9', 'Some post text 9.', 90),
    ('Title #10', 'Some post text 10.', 0),
    ('Title #11', 'Some post text 11.', 10),
    ('Title #12', 'Some post text 12.', 20),
    ('Title #13', 'Some post text 13.', 30),
    ('Title #14', 'Some post text 14,', 40),
    ('Title #15', 'Some post text 15.', 50);

insert into comments(post_id, text)
values
    (1L, 'Some comment 1.'),
    (1L, 'Some comment 2.'),
    (1L, 'Some comment 3.'),
    (2L, 'Some comment 4.'),
    (3L, 'Some comment 5.');