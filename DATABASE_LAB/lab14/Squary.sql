1.
更新：
use school
go

begin tran
  update STUDENTS set grade=1990 where sid='800001216'
  waitfor delay '00:00:20' -- 延时20秒
  select * from STUDENTS where sid='800001216'
rollback tran
select * from STUDENTS where sid='800001216'

查询：
use School
go

set transaction isolation level read uncommitted
--模拟实现脏读
select * from students where sid='800001216'
if @@ROWCOUNT<>0
	begin
		waitfor delay '00:00:20'
		-- PRINT 模拟实现不可重复读
		select * from STUDENTS where sid='800001216'
	end
	
2.
use School
go

set transaction isolation level read committed
--模拟实现脏读
select * from students where sid='800001216'
if @@ROWCOUNT<>0
	begin
		waitfor delay '00:00:20'
		-- PRINT 模拟实现不可重复读
		select * from STUDENTS where sid='800001216'
	end
	
3.
查询：
use School
insert into STUDENTS values('800000000','abc','aa@aa.com',1990)
set transaction isolation level repeatable read
begin tran
select * from students where sid='800000000'
if @@ROWCOUNT<>0
	begin
		waitfor delay '00:00:10'
		select * from STUDENTS where sid='800000000'
	end
rollback tran

删除：
set transaction isolation level repeatable read
delete from STUDENTS where sid='800000000'

4.
查询：
use School
set transaction isolation level serializable
begin tran
select * from students where sid='800000000'
waitfor delay '00:00:10'
select * from STUDENTS where sid='800000000'
rollback tran

插入：
set transaction isolation level serializable
insert into STUDENTS values('800000000','abc','aa@aa.com',1990)





