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
create trigger T4 on Worker
for insert as
if (select sage from inserted) < (select max(sage) from Worker)
begin
	print '新插入的值必须比表中已记录的最大sage值大。'
	Rollback transaction
end

2.
insert into worker values('00002','haha','F', 1, '开发部')

3.
create trigger T5 on Worker
for update as
if(select sage from inserted)<=(select sage from deleted)
begin
	print '工资只能升不能降！'
	Rollback transaction
end

4.
update worker set sage=7 where number='00001'

5.
create trigger T6 on Worker
for update,delete as
if(select Number from deleted)='00001'
begin
	print '不能修改编号是00001的记录'
	Rollback transaction
end

6.
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

create view StudentStucard as
select st.sid, st.sname, sc.card_id
from STUDENTS st, Stu_Card sc
where st.sid=sc.stu_id

insert into StudentStucard values('00010','ha',12);



create trigger T7 on StudentStucard
INSTEAD OF INSERT as
BEGIN
	SET NOCOUNT ON
	IF(not exists(
		select s.sid from students s, inserted i
		where s.sid=i.sid)
	  )
	 begin
		insert into students
			select sid, sname,null,null from inserted
		insert into Stu_Card
			select card_id,sid,null from inserted
	end
	else print'数据已存在'
END
