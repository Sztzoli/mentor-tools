create table lessons_completion
(
    student_id      bigint       not null,
    lesson_id       bigint       not null,
    video_status    varchar(255) not null,
    exercise_status varchar(255) not null,
    video_date      date         ,
    exercise_date   date        ,
    commit_url      varchar(255),
    primary key (student_id, lesson_id)
);
alter table lessons_completion
    add CONSTRAINT FK_LessonsLeComp foreign key (lesson_id) references lessons(id);
alter table lessons_completion
    add CONSTRAINT FK_StudentLeComp foreign key (student_id) references students(id);