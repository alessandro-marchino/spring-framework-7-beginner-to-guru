drop table if exists beer;
drop table if exists customer;

create table beer (
  id varchar(36) not null,
  beer_name varchar(50) not null,
  beer_style tinyint not null check ((beer_style between 0 and 9)),
  upc varchar(255) not null,
  price decimal(38,2) not null,
  quantity_on_hand integer,
  version integer,
  created_date datetime(6),
  updated_date datetime(6),
  primary key (id)
) engine=InnoDB;

create table customer (
  id varchar(36) not null,
  customer_name varchar(255),
  version integer,
  created_date datetime(6),
  updated_date datetime(6),
  primary key (id)
) engine=InnoDB;
