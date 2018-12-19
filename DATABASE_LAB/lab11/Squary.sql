实验准备：
1.
USE School
create table Worker(
	Number char(5),
	Name char(8) constraint U1 unique,
	Sex char(1),
	Sage int constraint U2 check (Sage<=28),
	Department char(20),
	constraint PK_Worker Primary Key (Number))

Insert into Worker(Number,Name,Sex,Sage,Department)
Values('00001','李勇','M',14,'科技部')
select * from worker

实验过程：
1.
USE School
alter table worker
add constraint U3 check (sage>=0)

2.
USE School
insert into worker(Number,Name,Sex,Sage,Department)
values('00002','freedom','M',-1,'未来部')

3.
USE School
insert into worker(Number,Name,Sex,Sage,Department)
values('00002','freedom','M',20,'未来部')
select * from worker

4.
USE School
alter table worker
add constraint U4 check (sage<0)

5.
USE School
alter table worker drop U2
go
create rule R2 as @value between 1 and 100
go
exec sp_bindrule R2  "worker.[sage]"

6.
USE School
Insert into Worker(Number,Name,Sex,Sage,Department)
Values('00003','sbzh','M',101,'回炉部')
select * from worker

7.
USE School
exec sp_unbindrule 'worker.[sage]'
Insert into Worker(Number,Name,Sex,Sage,Department)
Values('00003','sbzh','M',101,'回炉部')
select * from worker

8.
Insert into Worker(Number,Name,Sex,Sage,Department)
Values('00004','王勇','M',38,'科技部')
go
create rule R3 as @value >= 50
go
exec sp_bindrule R3, "worker.[sage]"
