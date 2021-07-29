alter table training_classes add column syllabus_id bigint;
alter table training_classes add CONSTRAINT FK_SyllabusTrainingClass foreign key (syllabus_id) references syllabuses(id);