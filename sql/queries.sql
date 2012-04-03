

(SELECT S2.sid
FROM Student S2)
MINUS
(SELECT S.sid
FROM Attempt A, Exercise E, Student S
WHERE E.ename='HW1' AND E.eid=A.eid AND A.submittime IS NOT NULL AND A.sid=S.sid
GROUP BY S.sid);

/* */

SELECT DISTINCT S.sid
FROM Attempt A, Exercise E, Student S
WHERE E.ename='HW1' AND E.eid=A.eid AND A.points=(E.correct_points*E.question_count) AND A.sid=S.sid AND A.attnum=1;

/* */

