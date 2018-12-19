1.
create table CARD(
	cid char(10) NOT NULL UNIQUE,
	sid char(10),
	money decimal(10,2)
)
alter table CARD add
  Constraint FK_CARD_CID FOREIGN KEY (sid)
    references students(sid) on delete cascade
	
2.ALTER TABLE CARD ADD bank CHAR(20)

3.ALTER TABLE CARD drop column bank 

4.
SELECT TEACHERS.tname,COURSES.cname
FROM TEACHERS,CHOICES,COURSES
WHERE  CHOICES.tid=TEACHERS.tid AND CHOICES.cid=COURSES.cid
	AND TEACHERS.salary>=ALL
	(
		SELECT salary
		FROM TEACHERS
		WHERE salary IS NOT NULL
	)
ORDER BY TEACHERS.tid

5.
select distinct X.CNAME 
FROM COURSES AS X,COURSES AS Y,COURSES AS Z
WHERE (Y.cname='C++'	AND X.hour=Y.hour)  
OR (Z.cname='UML' AND X.hour=Z.hour)

6.
SELECT SID
FROM CHOICES
WHERE CHOICES.cid=
(
	SELECT COURSES.cid
	FROM COURSES
	WHERE COURSES.cname='C++'
)
EXCEPT
SELECT SID
FROM CHOICES
WHERE CHOICES.cid=
(
	SELECT COURSES.cid
	FROM COURSES
	WHERE COURSES.cname='database'
)
7.
SELECT distinct STUDENTS.sid
FROM STUDENTS,CHOICES,COURSES
WHERE  CHOICES.cid=COURSES.cid
	AND STUDENTS.sid=CHOICES.sid
	AND COURSES.cname='database'
	AND CHOICES.score>ALL
	(
		SELECT CHOICES.score
		FROM CHOICES,COURSES,STUDENTS
		WHERE CHOICES.cid=COURSES.cid 
		      AND CHOICES.sid=STUDENTS.sid
		      AND STUDENTS.sname='ruvldjlm'
			  AND COURSES.cname='database'
			  AND CHOICES.score IS NOT NULL
	)
ORDER BY STUDENTS.sid
8.
select MAX(CHOICES.SCORE)as '最高成绩', AVG(CHOICES.SCORE) as '平均成绩'
from CHOICES, COURSES
where CHOICES.cid=COURSES.cid
AND COURSES.cname='database'
9.
SELECT distinct STUDENTS.sname
FROM STUDENTS,CHOICES,COURSES
WHERE  CHOICES.cid=COURSES.cid
	AND STUDENTS.sid=CHOICES.sid
	AND COURSES.cname='database'
	AND CHOICES.score>=ALL
	(
		SELECT CHOICES.score
		FROM CHOICES,COURSES,STUDENTS
		WHERE CHOICES.cid=COURSES.cid 
		      AND CHOICES.sid=STUDENTS.sid
			  AND COURSES.cname='database'
			  AND CHOICES.score IS NOT NULL
	)
10.
SELECT sname
FROM STUDENTS
WHERE sname NOT IN
	(
		SELECT STUDENTS.sname
		FROM COURSES,STUDENTS,CHOICES
		WHERE COURSES.cname='database'
		      AND COURSES.cid=CHOICES.cid
		      AND CHOICES.sid=STUDENTS.sid
	)
11.
select sname
from CHOICES,STUDENTS
where CHOICES.sid=STUDENTS.sid
GROUP by STUDENTS.sid,sname
HAVING COUNT(CHOICES.NO)>2

12.
CREATE VIEW VIEW_SC
AS SELECT STUDENTS.sname,COURSES.cname,CHOICES.score
from CHOICES,STUDENTS,COURSES
WHERE CHOICES.sid=STUDENTS.sid AND CHOICES.cid=COURSES.cid
go
SELECT *FROM VIEW_SC
13
SELECT sname
FROM VIEW_SC
WHERE score > 90

15.
create rule R1 as @value>0
go
exec sp_bindrule R1, "CARD.[money]"

16.
Insert into CARD(cid, sid, money)
Values('1', '800001216', -1.0)

17.
exec sp_unbindrule 'CARD.[money]'

18.
create trigger T1 on STUDENTS
for delete as
if(select sid from deleted)='800015960'
begin
	print '不能删除编号是800015960的记录'
	Rollback transaction
end

19.
delete from CHOICES
where sid='800015960'
delete from STUDENTS
where sid='800015960'

20.
set XACT_ABORT ON
begin tran update_stu
	update students
	set sname='haha'
	where sid='800001216'
	begin tran insert_courses
		insert into COURSES
		values('10001','database2',96)
	commit tran insert_courses
commit tran update_stu	





