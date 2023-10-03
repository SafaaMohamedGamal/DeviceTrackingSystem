create table sequence
(
id int not null,
seq_name varchar(20),
seq_value int
);
ALTER TABLE sequence ADD PRIMARY KEY (id);

create table device
(
id int not null,
pin_code varchar(7),
temperature int,
status_id int not null
);
ALTER TABLE device ADD PRIMARY KEY (id);
ALTER TABLE device ADD constraint U_PIN_CODE UNIQUE (pin_code);

create table status
(
id int not null,
name varchar(10)
);

ALTER TABLE status ADD PRIMARY KEY (id);
ALTER TABLE device ADD constraint FK_status FOREIGN KEY (status_id) references status(id);
