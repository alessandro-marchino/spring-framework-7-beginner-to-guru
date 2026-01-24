drop table if exists beer_category;
drop table if exists category;

create table category (
  id varchar(36) not null primary key,
  description varchar(50),
  version integer,
  created_date datetime(6),
  last_modified_date datetime(6)
) engine=InnoDB;

create table beer_category (
  beer_id varchar(36),
  category_id varchar(36),
  primary key (beer_id, category_id),
  foreign key (beer_id) REFERENCES beer(id),
  foreign key (category_id) REFERENCES category(id)
) engine=InnoDB;
