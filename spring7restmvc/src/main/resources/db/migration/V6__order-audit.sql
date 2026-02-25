drop table if exists beer_audit;

create table beer_audit (
  audit_id varchar(36) not null primary key,
  id varchar(36) not null,
  beer_name varchar(50),
  beer_style tinyint,
  upc varchar(255),
  price decimal(38,2),
  quantity_on_hand integer,
  version integer,
  created_date datetime(6),
  updated_date datetime(6),
  created_date_audit datetime(6),
  principal_name varchar(255),
  audit_event_type varchar(255)
) engine=InnoDB;
