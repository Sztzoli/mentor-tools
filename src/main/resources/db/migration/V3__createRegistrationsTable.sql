create table registrations
(
    student_id        bigint not null,
    training_class_id bigint not null,
    status            varchar(255),
    primary key (student_id, training_class_id)
)