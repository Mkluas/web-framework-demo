create table t_admin
(
	id int(32) auto_increment
		primary key,
	account varchar(128) null,
	password varchar(128) null,
	salt varchar(128) null,
	username varchar(128) default '' null,
	mobile varchar(128) default '' null,
	email varchar(128) default '' null,
	creator varchar(128) default '' null,
	ct bigint(40) null,
	ut bigint(40) null,
	version int(32) default '0' null
)
engine=InnoDB charset=utf8
;