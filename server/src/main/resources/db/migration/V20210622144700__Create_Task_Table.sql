create table task (
    id integer primary key,
    title text not null,
    description text not null,
    is_done boolean not null default false,
    is_delete boolean not null default false
)