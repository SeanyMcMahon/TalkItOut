# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table problem (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  conversation              varchar(255),
  resources                 varchar(255),
  solution                  varchar(255),
  user_email                varchar(255),
  constraint pk_problem primary key (id))
;

create table user (
  email                     varchar(255) not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence problem_seq;

create sequence user_seq;

alter table problem add constraint fk_problem_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_problem_user_1 on problem (user_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists problem;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists problem_seq;

drop sequence if exists user_seq;

