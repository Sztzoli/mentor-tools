create table syllabuses_modules
(
    syllabus_id bigint not null,
    modules_id  bigint not null,
    primary key (syllabus_id, modules_id)
);
alter table syllabuses_modules add constraint FK_ModuleSyl_Mod foreign key (modules_id) references modules(id);
alter table syllabuses_modules add constraint FK_SyllabusSyl_Mod foreign key (syllabus_id) references syllabuses(id);