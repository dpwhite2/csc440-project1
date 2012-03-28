
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

EXIT;
