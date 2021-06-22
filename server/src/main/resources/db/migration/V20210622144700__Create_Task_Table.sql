create table task (
    id serial primary key,
    title varchar not null,
    description varchar not null,
    is_done boolean not null default false,
    is_delete boolean not null default false
)