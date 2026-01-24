drop table if exists beer_order_shipment;

create table beer_order_shipment (
  id varchar(36) not null primary key,
  beer_order_id varchar(36) unique,
  tracking_number varchar(50),
  version integer,
  created_date datetime(6),
  last_modified_date datetime(6),
  foreign key (beer_order_id) REFERENCES beer_order(id)
) engine=InnoDB;

alter table beer_order add column beer_order_shipment_id varchar(36);
alter table beer_order add constraint bos_shipment_fk foreign key(beer_order_shipment_id) references beer_order_shipment(id);
