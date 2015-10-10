# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table following (
  combo_id                  bigint not null,
  follower_user_id          bigint not null,
  following                 varchar(255) not null,
  constraint pk_following primary key (combo_id))
;

create table users (
  user_id                   bigint not null,
  username                  varchar(255) not null,
  password                  varchar(255) not null,
  constraint uq_users_username unique (username),
  constraint pk_users primary key (user_id))
;

create table watched_companies (
  combo_id                  bigint not null,
  user_user_id              bigint not null,
  symbol                    varchar(255) not null,
  constraint pk_watched_companies primary key (combo_id))
;

create sequence following_seq;

create sequence users_seq;

create sequence watched_companies_seq;

alter table following add constraint fk_following_follower_1 foreign key (follower_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_following_follower_1 on following (follower_user_id);
alter table watched_companies add constraint fk_watched_companies_user_2 foreign key (user_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_watched_companies_user_2 on watched_companies (user_user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists following;

drop table if exists users;

drop table if exists watched_companies;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists following_seq;

drop sequence if exists users_seq;

drop sequence if exists watched_companies_seq;

