-- auto-generated definition
create table course
(
    id   int auto_increment
        primary key,
    name varchar(200) null,
    code varchar(4)   null
);

-- auto-generated definition
create table student
(
    id        int auto_increment
        primary key,
    rut       varchar(10)  null,
    name      varchar(200) null,
    last_name varchar(200) null,
    age       int          null,
    course_id int          not null,
    constraint course_id
        foreign key (course_id) references course (id)
);
