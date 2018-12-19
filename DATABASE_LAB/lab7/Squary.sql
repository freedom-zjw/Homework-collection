1. 
GRANT SELECT
ON STUDENTS
to PUBLIC

2.
GRANT SELECT,UPDATE
ON COURSES
to USER1,USER2,USER3

3.
grant select,update(salary) 
on teachers 
to USER1 
with grant option

4.
GRANT SELECT,UPDATE(score) 
ON CHOICES
TO USER2

5.
grant select 
on teachers 
to user2 
with grant option

6.
USER2:
grant select 
on teachers 
to user3 
with grant option 

USER3:
grant select 
on teachers 
to user2
with grant option 

7.
REVOKE SELECT
ON STUDENTS
FROM PUBLIC

8.
revoke select,update
on COURSES
from USER1,USER2



