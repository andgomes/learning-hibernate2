create table message (
	message_id integer,
	message_text varchar(100),
	next_message_id integer,
	primary key (message_id),
	foreign key (next_message_id) references message (message_id)
);
