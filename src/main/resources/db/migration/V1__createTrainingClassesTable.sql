create table training_classes
(
    id         bigint auto_increment,
    name       varchar(255) not null,
    end_date   date         not null,
    start_date date         not null,
    primary key (id)
)