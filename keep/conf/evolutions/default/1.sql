# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table card (
  id                            bigint auto_increment not null,
  user                          TEXT,
  content                       TEXT,
  title                         TEXT,
  timestamp                     timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  reminder                      TEXT,
  is_reminder_active            int default 0,
  is_archive                    int default 0,
  positions                     int default 0,
  constraint pk_card primary key (id)
);

create table registration (
  user_id                       bigint auto_increment not null,
  user_name                     varchar(255),
  user_email                    varchar(255),
  user_password                 varchar(255),
  timestamp                     timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  constraint pk_registration primary key (user_id)
);


# --- !Downs

drop table if exists card;

drop table if exists registration;

