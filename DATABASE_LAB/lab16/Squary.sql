1.
exec sp_addlogin '王二', '123456', 'School', 'English'
go 
use School
go
exec sp_grantdbaccess '王二'

use School
go
create view grade2000 as
	select * from STUDENTS
	where grade=2000

2.
use School
go
grant select on grade2000 to 王二

use School
go
select * from grade2000

3.
use School
go
grant update on dbo.[grade2000]([sname])
	to 王二

use School
go
update grade2000 set sname='abcd' where sid='800000000'

4.


