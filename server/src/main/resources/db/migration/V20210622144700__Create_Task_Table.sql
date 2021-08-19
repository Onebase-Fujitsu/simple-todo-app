create table task (
    id int primary key auto_increment,
    title varchar not null,
    description varchar not null,
    is_done boolean not null default false,
    is_delete boolean not null default false,
    created_at timestamp not null,
    updated_at timestamp not null
)