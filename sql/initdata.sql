
INSERT INTO UserInfo VALUES (1,'Ssbudha','123','student');
INSERT INTO Student VALUES ('Ssbudha',1);
INSERT INTO UserInfo VALUES (2,'Sskanit','123','student');
INSERT INTO Student VALUES ('Sskanit',2);
INSERT INTO UserInfo VALUES (3,'Agholak','123','student');
INSERT INTO Student VALUES ('Agholak',3);
INSERT INTO UserInfo VALUES (4,'Rjoseph','123','student');
INSERT INTO Student VALUES ('Rjoseph',4);
INSERT INTO UserInfo VALUES (5,'Tbirajd','123','student');
INSERT INTO Student VALUES ('Tbirajd',5);
 
-- Ogan 
INSERT INTO UserInfo VALUES (6, 'Ogan', '123', 'prof');
INSERT INTO Professor VALUES (1, 6);

-- Chirkova
INSERT INTO UserInfo VALUES (7, 'Chirkova', '123', 'prof');
INSERT INTO Professor VALUES (2, 7); 

-- Mueller 
INSERT INTO UserInfo VALUES (8, 'Mueller', '123', 'prof');
INSERT INTO Professor VALUES (3, 8);

INSERT INTO Topic VALUES (1, 'Database Fundamentals');
INSERT INTO Topic VALUES (2, 'Security and Authorization');
INSERT INTO Topic VALUES (3, 'ER Design and other topics');
INSERT INTO Topic VALUES (4, 'Binary search trees and Btrees');
INSERT INTO Topic VALUES (5, 'Hashing');
INSERT INTO Topic VALUES (6, 'Files and indexing and other topics');
INSERT INTO Topic VALUES (7, 'Processes and Threads');
INSERT INTO Topic VALUES (8, 'Memory Organization');
INSERT INTO Topic VALUES (9, 'Deadlocks and other topics');

INSERT INTO Course VALUES ('CSC440', 'CSC440SPR12', 'Database Systems', '01-JAN-2012', '10-MAY-2012', 1);
INSERT INTO Course VALUES ('CSC541', 'CSC541FLL11', 'Advanced Data Structures', '01-AUG-2011', '15-DEC-2011', 2);
INSERT INTO Course VALUES ('CSC501', 'CSC501SPR12', 'Operating Systems', '01-JAN-2012', '10-MAY-2012', 3);

INSERT INTO TopicPerCourse VALUES ('CSC440', 1);
INSERT INTO TopicPerCourse VALUES ('CSC440', 2);
INSERT INTO TopicPerCourse VALUES ('CSC440', 3);
INSERT INTO TopicPerCourse VALUES ('CSC541', 4);
INSERT INTO TopicPerCourse VALUES ('CSC541', 5);
INSERT INTO TopicPerCourse VALUES ('CSC541', 6);
INSERT INTO TopicPerCourse VALUES ('CSC501', 7);
INSERT INTO TopicPerCourse VALUES ('CSC501', 8);
INSERT INTO TopicPerCourse VALUES ('CSC501', 9);

INSERT INTO Enrolled VALUES ('CSC440', 'Ssbudha');
INSERT INTO Enrolled VALUES ('CSC440', 'Sskanit');
INSERT INTO Enrolled VALUES ('CSC440', 'Agholak');
INSERT INTO Enrolled VALUES ('CSC440', 'Rjoseph');
INSERT INTO Enrolled VALUES ('CSC501', 'Agholak');

INSERT INTO Exercise VALUES (1, 'CSC440', 'HW1', '12-FEB-2012', '01-MAR-2012', 3, 1, 0, 'first attempt', 3);
INSERT INTO Exercise VALUES (2, 'CSC440', 'HW2', '12-MAR-2012', '01-APR-2012', 5, 2, 0, 'average', 2);

INSERT INTO Question VALUES ('Q1', 'Question 1', 1, 'HINT', -1, -1, 'EXPLANATION');
INSERT INTO Question VALUES ('Q2', 'Question 2', 1, 'HINT', -1, -1, 'EXPLANATION');
INSERT INTO Question VALUES ('Q3', 'Question 3', 1, 'HINT', -1, -1, 'EXPLANATION');

INSERT INTO Answer VALUES ('Q1', 1, 'Correct 1.1', 1, 'Explanation 1.1', 'Hint 1.1');
INSERT INTO Answer VALUES ('Q1', 2, 'Correct 1.2', 1, 'Explanation 1.2', 'Hint 1.2');
INSERT INTO Answer VALUES ('Q1', 3, 'Correct 1.3', 1, 'Explanation 1.3', 'Hint 1.3');
INSERT INTO Answer VALUES ('Q1', 4, 'Incorrect 1.1', 0, '', '');
INSERT INTO Answer VALUES ('Q1', 5, 'Incorrect 1.2', 0, '', '');
INSERT INTO Answer VALUES ('Q1', 6, 'Incorrect 1.3', 0, '', '');
INSERT INTO Answer VALUES ('Q1', 7, 'Incorrect 1.4', 0, '', '');
INSERT INTO Answer VALUES ('Q1', 8, 'Incorrect 1.5', 0, '', '');
INSERT INTO Answer VALUES ('Q1', 9, 'Incorrect 1.6', 0, '', '');
INSERT INTO Answer VALUES ('Q1', 10, 'Incorrect 1.7', 0, '', '');

INSERT INTO Answer VALUES ('Q2', 11, 'Correct 2.1', 1, 'Explanation 2.1', 'Hint 2.1');
INSERT INTO Answer VALUES ('Q2', 12, 'Correct 2.2', 1, 'Explanation 2.2', 'Hint 2.2');
INSERT INTO Answer VALUES ('Q2', 13, 'Correct 2.3', 1, 'Explanation 2.3', 'Hint 2.3');
INSERT INTO Answer VALUES ('Q2', 14, 'Incorrect 2.1', 0, '', '');
INSERT INTO Answer VALUES ('Q2', 15, 'Incorrect 2.2', 0, '', '');
INSERT INTO Answer VALUES ('Q2', 16, 'Incorrect 2.3', 0, '', '');
INSERT INTO Answer VALUES ('Q2', 17, 'Incorrect 2.4', 0, '', '');
INSERT INTO Answer VALUES ('Q2', 18, 'Incorrect 2.5', 0, '', '');
INSERT INTO Answer VALUES ('Q2', 19, 'Incorrect 2.6', 0, '', '');
INSERT INTO Answer VALUES ('Q2', 20, 'Incorrect 2.7', 0, '', '');

INSERT INTO Answer VALUES ('Q3', 21, 'Correct 3.1', 1, 'Explanation 3.1', 'Hint 3.1');
INSERT INTO Answer VALUES ('Q3', 22, 'Correct 3.2', 1, 'Explanation 3.2', 'Hint 3.2');
INSERT INTO Answer VALUES ('Q3', 23, 'Correct 3.3', 1, 'Explanation 3.3', 'Hint 3.3');
INSERT INTO Answer VALUES ('Q3', 24, 'Incorrect 3.1', 0, '', '');
INSERT INTO Answer VALUES ('Q3', 25, 'Incorrect 3.2', 0, '', '');
INSERT INTO Answer VALUES ('Q3', 26, 'Incorrect 3.3', 0, '', '');
INSERT INTO Answer VALUES ('Q3', 27, 'Incorrect 3.4', 0, '', '');
INSERT INTO Answer VALUES ('Q3', 28, 'Incorrect 3.5', 0, '', '');
INSERT INTO Answer VALUES ('Q3', 29, 'Incorrect 3.6', 0, '', '');
INSERT INTO Answer VALUES ('Q3', 30, 'Incorrect 3.7', 0, '', '');

INSERT INTO ExerciseQuestion VALUES (1, 'Q2');
INSERT INTO ExerciseQuestion VALUES (1, 'Q3');
INSERT INTO ExerciseQuestion VALUES (2, 'Q1');
INSERT INTO ExerciseQuestion VALUES (2, 'Q2');

-- Ssbudha HW2 Attempt 1
INSERT INTO Attempt VALUES (1, 2, 'Ssbudha', 1, '20-MAR-2012', 0);

INSERT INTO AttemptQuestion VALUES (1, 1, 'Q1', 1, '', 0);
INSERT INTO AttemptAnswer VALUES (1, 1, 1, 1);
INSERT INTO AttemptAnswer VALUES (1, 1, 2, 4);
INSERT INTO AttemptAnswer VALUES (1, 1, 3, 5);
INSERT INTO AttemptAnswer VALUES (1, 1, 4, 6);
INSERT INTO AttemptQuestion VALUES (1, 2, 'Q2', 3, '', 0);
INSERT INTO AttemptAnswer VALUES (1, 2, 1, 14);
INSERT INTO AttemptAnswer VALUES (1, 2, 2, 15);
INSERT INTO AttemptAnswer VALUES (1, 2, 3, 11);
INSERT INTO AttemptAnswer VALUES (1, 2, 4, 16);

-- Sskanit HW1 Attempt 1
INSERT INTO Attempt VALUES (2, 1, 'Sskanit', 1, '20-FEB-2012', 0);

INSERT INTO AttemptQuestion VALUES (2, 1, 'Q3', 1, '', 0);
INSERT INTO AttemptAnswer VALUES (2, 1, 1, 21);
INSERT INTO AttemptAnswer VALUES (2, 1, 2, 24);
INSERT INTO AttemptAnswer VALUES (2, 1, 3, 25);
INSERT INTO AttemptAnswer VALUES (2, 1, 4, 26);
INSERT INTO AttemptQuestion VALUES (2, 2, 'Q2', 3, '', 0);
INSERT INTO AttemptAnswer VALUES (2, 2, 1, 14);
INSERT INTO AttemptAnswer VALUES (2, 2, 2, 15);
INSERT INTO AttemptAnswer VALUES (2, 2, 3, 12);
INSERT INTO AttemptAnswer VALUES (2, 2, 4, 16);

-- Sskanit HW1 Attempt 2
INSERT INTO Attempt VALUES (3, 1, 'Sskanit', 2, '21-FEB-2012', 0);

INSERT INTO AttemptQuestion VALUES (3, 1, 'Q2', 1, '', 0);
INSERT INTO AttemptAnswer VALUES (3, 1, 1, 13);
INSERT INTO AttemptAnswer VALUES (3, 1, 2, 19);
INSERT INTO AttemptAnswer VALUES (3, 1, 3, 18);
INSERT INTO AttemptAnswer VALUES (3, 1, 4, 17);
INSERT INTO AttemptQuestion VALUES (3, 2, 'Q3', 2, '', 0);
INSERT INTO AttemptAnswer VALUES (3, 2, 1, 29);
INSERT INTO AttemptAnswer VALUES (3, 2, 2, 22);
INSERT INTO AttemptAnswer VALUES (3, 2, 3, 28);
INSERT INTO AttemptAnswer VALUES (3, 2, 4, 27);

-- Agholak HW1 Attempt 1
INSERT INTO Attempt VALUES (4, 1, 'Agholak', 1, '19-FEB-2012', 0);

INSERT INTO AttemptQuestion VALUES (4, 1, 'Q3', 4, '', 0);
INSERT INTO AttemptAnswer VALUES (4, 1, 1, 21);
INSERT INTO AttemptAnswer VALUES (4, 1, 2, 24);
INSERT INTO AttemptAnswer VALUES (4, 1, 3, 25);
INSERT INTO AttemptAnswer VALUES (4, 1, 4, 26);
INSERT INTO AttemptQuestion VALUES (4, 2, 'Q2', 4, '', 0);
INSERT INTO AttemptAnswer VALUES (4, 2, 1, 16);
INSERT INTO AttemptAnswer VALUES (4, 2, 2, 15);
INSERT INTO AttemptAnswer VALUES (4, 2, 3, 17);
INSERT INTO AttemptAnswer VALUES (4, 2, 4, 13);

EXIT;
