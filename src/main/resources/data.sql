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

