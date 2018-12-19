1.
create table class(
	class_id varchar(4) NOT NULL UNIQUE,
	name varchar(10),
	department varchar(20),
	CONSTRAINT PK_class PRIMARY KEY(class_id)
);

2.
use School
set xact_abort on
begin transaction T3
	insert into class values('0001','01CSC','CS')
	set xact_abort off
	begin transaction T4
		insert into class values('0001','01CSC','CS')
	commit transaction T4
commit transaction T3