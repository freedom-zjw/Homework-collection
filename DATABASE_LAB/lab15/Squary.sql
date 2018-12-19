1.
use School
set transaction isolation level repeatable read
begin tran
	update STUDENTS set sname='db' where sid='800000000'
	
use School
set transaction isolation level repeatable read
begin tran
	select * from students  where sid='800000000'
commit tran

exec sp_who

use School
set transaction isolation level repeatable read
set lock_timeout 2000
begin tran
	select * from students  where sid='800000000'
commit tran
	
2.

set transaction isolation level repeatable read
begin tran
	select * from students where sid='800000000'
	waitfor delay '00:00:05'
	update STUDENTS set grade=1990 where  sid='800000000'
commit tran
	select * from students where sid='800000000'


	
3.
set transaction isolation level repeatable read
begin tran
begin try
	select * from students where sid='800000000'
	waitfor delay '00:00:05'
	update STUDENTS set grade=2000 where  sid='800000000'
commit tran
end try
begin catch
  SELECT 'There was an error! ' + ERROR_MESSAGE()
  return
end catch



