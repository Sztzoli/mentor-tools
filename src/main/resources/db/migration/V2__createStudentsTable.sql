create table students
(
    id              bigint auto_increment,
    comment         varchar(255),
    email           varchar(255) not null,
    github_username varchar(255) not null,
    name            varchar(255) not null,
    primary key (id)
)