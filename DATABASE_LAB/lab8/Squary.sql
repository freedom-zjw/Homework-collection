1.
SELECT COUNT(*) 
FROM CHOICES as X, COURSES as Y
WHERE X.cid=Y.cid and Y.cname='C++' and X.score>=60

SELECT COUNT(*) 
FROM CHOICES as X, COURSES as Y
WHERE X.cid=Y.cid and Y.cname='C++' and X.score<60

SELECT COUNT(*) 
FROM CHOICES as X, COURSES as Y
WHERE X.cid=Y.cid and Y.cname='C++' and score IS NULL

2.
SELECT sid,score
FROM CHOICES as X, COURSES as Y
WHERE X.cid=Y.cid and Y.cname='C++'
ORDER BY score


3.
SELECT DISTINCT sid,score
FROM CHOICES as X, COURSES as Y
WHERE X.cid=Y.cid and Y.cname='C++'
ORDER BY score

4.
SELECT grade
from STUDENTS
GROUP BY grade
ORDER BY grade

5.
SELECT cid,AVG(SCORE)as AverageScore,COUNT(*)as Total,MAX(score) as MaxScore,
		MIN(score) as MinScore
FROM CHOICES
WHERE score IS NOT NULL
GROUP BY cid

6.
SELECT DISTINCT grade 
from STUDENTS
WHERE  grade >= ALL(
	SELECT DISTINCT grade
	from STUDENTS
)

SELECT DISTINCT grade 
from STUDENTS
WHERE  grade >= ALL(
	SELECT DISTINCT grade
	from STUDENTS
	where grade is not null
)