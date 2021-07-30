create table lessons
(
    id        bigint auto_increment,
    title     varchar(255) not null,
    url       varchar(255) not null,
    module_id bigint,
    primary key (id)
);
alter table lessons
    add constraint FK_ModulesLessons foreign key (module_id) references modules (id);