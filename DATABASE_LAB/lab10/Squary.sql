实验准备：
1.
USE School
CREATE TABLE Stu_Union(
	sno CHAR(5) NOT NULL UNIQUE,
	sname CHAR(8),
	ssex CHAR(1),
	sage INT,
	sdept CHAR(20),
	CONSTRAINT PK_Stu_Union PRIMARY KEY(sno) 
);
insert into Stu_Union values ('10001','李勇','0',24,'EE');
insert into Stu_Union values ('95002','王敏','1',23,'CS');
insert into Stu_Union values ('95003','王浩','0',25,'EE');
insert into Stu_Union values ('95005','王杰','0',25,'EE');
insert into Stu_Union values ('95009','李勇','0',25,'EE');
select * from Stu_Union;

2.
USE School
CREATE TABLE Course(
	cno CHAR(4) NOT NULL UNIQUE,
	cname CHAR(50),
	cpoints int,
	CONSTRAINT PK primary key (cno) 
);
insert into Course values ('0001','ComputerNetworks',2);
insert into Course values ('0002','Database',3);
select * from Course;

3.
USE School
CREATE TABLE SC(
	sno char(5) REFERENCES Stu_Union (sno) on delete cascade,
	cno char(4) REFERENCES Course(cno) on delete cascade,
	grade INT,
	CONSTRAINT PK_SC PRIMARY KEY (sno,cno)
);
insert into SC values ('95002','0001',2);
insert into SC values ('95002','0002',2);
insert into SC values ('10001','0001',2);
insert into SC values ('10001','0002',2);
select * from SC;

4.
USE School
CREATE TABLE Stu_Card(
	card_id char(14),
	stu_id char(10) references students(sid) on delete cascade,
	remained_money decimal (10,2),
	constraint PK_stu_card Primary key (card_id)
);
insert into Stu_Card values ('05212567','800001216',100.25);
insert into Stu_Card values ('05212222','800005753',200.50);
select * from Stu_Card;

5.
USE School
CREATE TABLE ICBC_Card(
	bank_id char(20),
	stu_card_id char(14) references stu_card(card_id) on delete cascade,
	restored_money decimal (10,2),
	constraint PK_Icbc_card Primary key (bank_id)
);
insert into ICBC_Card values ('9558844022312','05212567',15000.1);
insert into ICBC_Card values ('9558844023645','05212222',50000.3);
select * from ICBC_Card ;

实验过程：
1.
Alter table SC
	drop constraint FK__SC__cno__3D5E1FD2;
Alter table SC
	drop constraint FK__SC__sno__3C69FB99;
Alter table SC
	add constraint FK_SC_cno foreign key (cno)
		references Course(cno) on delete no action
Alter table SC
	add constraint FK_SC_sno foreign key (sno)
		references Stu_Union(sno) on delete no action
		
delete from Stu_Union where sno='10001'

2.
Alter table SC
	drop constraint FK_SC_cno;
Alter table SC
	drop constraint FK_SC_sno;
Alter table SC
	add constraint FK_SC_cno foreign key (cno)
		references Course(cno) on delete set NULL
Alter table SC
	add constraint FK_SC_sno foreign key (sno)
		references Stu_Union(sno) on delete set NULL

3.
Alter table ICBC_Card
	drop constraint FK__ICBC_Card__stu_c__46E78A0C;
Alter table ICBC_Card
	add constraint FK__ICBC_Card__stu_c foreign key (stu_card_id)
		references stu_card(card_id)  on delete set NULL

alter table choices add
  Constraint FK_CHOICES_STUDENTS FOREIGN KEY (sid)
    references students(sid) on delete cascade

Begin Transaction T3
  delete from STUDENTS where sid='800001216'
  select * from ICBC_

delete from STUDENTS where sid='800001216'

4.
USE School
create table help(
	sid char(10) not null primary key,
	sname varchar(30),
	help_id char(10) references students(sid) on delete cascade,
	FOREIGN KEY (sid) references students(sid)
);

5.
USE School
create table members(
	sid char(10),
	sname varchar(30),
	myleader char(10),
	constraint PK_leader primary key(sid)
)
create table monitor(
	sid char(10),
	sname varchar(30),
	mymember char(10),
	constraint PK_monitor primary key(sid),
	constraint PF_monitor foreign key(mymember )references members(sid) 
) 
alter table members
	add constraint FK_members foreign key(myleader)
		references monitor(sid)