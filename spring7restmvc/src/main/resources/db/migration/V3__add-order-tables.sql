drop table if exists beer_order_line;
drop table if exists beer_order;

create table beer_order (
  id varchar(36) not null,
  customer_id varchar(36),
  customer_ref varchar(255),
  version integer,
  created_date datetime(6),
  last_modified_date datetime(6),
  primary key (id),
  foreign key (customer_id) REFERENCES customer(id)
) engine=InnoDB;

create table beer_order_line (
  id varchar(36) not null,
  beer_id varchar(36),
  beer_order_id varchar(36),
  order_quantity integer,
  quantity_allocated integer,
  version integer,
  created_date datetime(6),
  last_modified_date datetime(6),
  primary key (id),
  foreign key (beer_id) REFERENCES beer(id),
  foreign key (beer_order_id) REFERENCES beer_order(id)
) engine=InnoDB;
