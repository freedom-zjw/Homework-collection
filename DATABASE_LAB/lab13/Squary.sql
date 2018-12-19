1.
set XACT_ABORT ON
begin tran update_stu
	update students
	set sname='haha'
	where sid='800001216'
	begin tran insert_tea
		insert into TEACHERS
		values('200003125','newTeacher','123@123.com',3000)
	commit tran insert_tea
commit tran update_stu	

2.
set XACT_ABORT OFF
begin tran update_tea
	update TEACHERS
	set salary=4000
	where tid='200003125'
	save tran update_tea_done
	insert into COURSES
	values('10001', 'database', 96)
	if @@ERROR!=0 or @@ROWCOUNT>1
	begin
		rollback tran update_tea_done
		print '插入courses表失败'
		return
	end
commit tran update_tea

3.
create procedure updatecourses
	@course_cid char(10),
	@course_hour int,
	@returnString varchar(100) out
as
begin tran t1
	if not exists(select cid from COURSES where cid=@course_cid)
		begin
			select @returnString='课程信息不存在'
			goto onError
		end
	update COURSES set hour=@course_hour where cid=@course_cid
	if @@ERROR<>0
		begin
			select @returnString='修改课时失败'
			goto onError
		end	
	select @returnString='修改课时成功'
	print @returnString
commit tran t1
onError:
	print @returnString
	rollback tran
go


declare @courseid char(10)
declare @hour int
declare @returnString varchar(100）
exec updatecoursehours '10000',12,@returnString out

declare @course_cid char(4)
declare	@course_hour int
declare	@returnString varchar(100) 
exec updatecourses '10001',12,@returnString out

