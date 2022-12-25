use trackingSystem;
create table sequence
(
id int,
seq_name varchar(20),
seq_value int
);
ALTER TABLE sequence ADD PRIMARY KEY (id);

create table device
(
id int,
pin_code varchar(7),
temprature int,
status_id int
);
ALTER TABLE device ADD PRIMARY KEY (id);
ALTER TABLE device ADD constraint U_pin_code UNIQUE device(pin_code);

create table status
(
id int,
name varchar(10)
);

ALTER TABLE status ADD PRIMARY KEY (id);
ALTER TABLE device ADD constraint FK_status FOREIGN KEY (status_id) references status(id);

insert into status values (1, 'Ready');
insert into status values (2, 'Active');

-------------------------------------------------------

insert into device values (1,'1112223', -1, 1);
insert into device values (2,'1113333', -1, 1);
insert into device values (3,'2223333', 3, 2);
insert into device values (4,'1114443', -1, 1);
insert into device values (5,'1115553', -1, 1);
insert into device values (6,'2225553', 4, 2);
insert into device values (7,'2226663', 6, 2);
insert into device values (8,'1116663', -1, 1);
insert into device values (9,'1117773', -1, 1);
insert into device values (10,'2227773', 2, 2);
insert into device values (11,'1118883', -1, 1);
commit;

insert into sequence values (1,'device_id', 12);
commit;

