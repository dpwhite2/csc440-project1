
/* Find students who did not take homework 1. */

(SELECT S2.sid
FROM Student S2, Enrolled En
WHERE S2.sid=En.sid AND En.cid='CSC440' /* HW1 is in CSC440*/
)
MINUS
(SELECT S.sid
FROM Attempt A, Exercise E, Student S
WHERE E.ename='HW1' AND E.eid=A.eid AND A.submittime IS NOT NULL AND A.sid=S.sid
GROUP BY S.sid);


/* Find students who scored the maximum score on the first attempt for homework 1. */

SELECT DISTINCT S.sid
FROM Attempt A, Exercise E, Student S
WHERE E.ename='HW1' AND E.eid=A.eid AND A.points=(E.correct_points*E.question_count) AND A.sid=S.sid AND A.attnum=1;


/* Find students who scored the maximum score on the first attempt for any homework. */

SELECT DISTINCT S.sid
FROM Attempt A, Exercise E, Student S
WHERE E.eid=A.eid AND A.points=(E.correct_points*E.question_count) AND A.sid=S.sid AND A.attnum=1;
