# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table person (
  id                            varchar(255) not null,
  name                          varchar(255),
  constraint pk_person primary key (id)
);

create table registration (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_registration primary key (id)
);

create table tasks (
  id                            bigint auto_increment not null,
  status                        varchar(255),
  user                          varchar(255),
  title                         varchar(255),
  description                   varchar(255),
  last_updated                  varchar(255),
  list                          varchar(255),
  list_tf                       varchar(255),
  constraint pk_tasks primary key (id)
);


# --- !Downs

drop table if exists person;

drop table if exists registration;

drop table if exists tasks;

