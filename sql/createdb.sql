
CREATE TABLE UserInfo (
    userid INTEGER,
    username VARCHAR2(128)      NOT NULL,
    password VARCHAR2(128)      NOT NULL,
    role VARCHAR2(32)           NOT NULL,
    PRIMARY KEY (userid),
    CONSTRAINT userinfo_role_enum CHECK (role IN ('prof','student')),
    UNIQUE (username)
);

CREATE SEQUENCE userinfo_ids START WITH 1000 INCREMENT BY 1;
SELECT userinfo_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE Student (
    sid VARCHAR2(64)            NOT NULL,
    userid INTEGER              NOT NULL,
    PRIMARY KEY (sid),
    FOREIGN KEY (userid) REFERENCES UserInfo
);

CREATE TABLE Professor (
    pid INTEGER                 NOT NULL,
    userid INTEGER              NOT NULL,
    PRIMARY KEY (pid),
    FOREIGN KEY (userid) REFERENCES UserInfo
);

CREATE SEQUENCE professor_ids START WITH 1000 INCREMENT BY 1;
SELECT professor_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE TA (
    taid INTEGER                NOT NULL,
    userid INTEGER              NOT NULL,
    PRIMARY KEY (taid),
    FOREIGN KEY (userid) REFERENCES UserInfo
);

CREATE SEQUENCE ta_ids START WITH 1000 INCREMENT BY 1;
SELECT ta_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE Topic (
    tid INTEGER                 NOT NULL,
    text VARCHAR2(1000)         NOT NULL,
    PRIMARY KEY (tid)
);

CREATE SEQUENCE topic_ids START WITH 1000 INCREMENT BY 1;
SELECT topic_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE Course (
    cid VARCHAR2(32)            NOT NULL,
    token VARCHAR2(32)          NOT NULL,
    cname VARCHAR2(1000)        NOT NULL,
    startdate DATE              NOT NULL,
    enddate DATE                NOT NULL,
    pid INTEGER                 NOT NULL,
    PRIMARY KEY (cid),
    UNIQUE (token),
    FOREIGN KEY (pid) REFERENCES Professor
);

CREATE TABLE Enrolled (
    cid VARCHAR2(32)            NOT NULL,
    sid VARCHAR2(64)            NOT NULL,
    PRIMARY KEY (cid, sid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (sid) REFERENCES Student
);

CREATE TABLE Assisting (
    cid VARCHAR2(32)            NOT NULL,
    taid INTEGER                NOT NULL,
    PRIMARY KEY (cid, taid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (taid) REFERENCES TA
);

CREATE TABLE TopicPerCourse (
    cid VARCHAR2(32)            NOT NULL,
    tid INTEGER                 NOT NULL,
    PRIMARY KEY (cid, tid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (tid) REFERENCES Topic
);

CREATE TABLE Exercise (
    eid                 INTEGER                     NOT NULL,
    cid                 VARCHAR2(32)                NOT NULL,
    ename               VARCHAR2(1000)  DEFAULT '',
    startdate           DATE                        NOT NULL,
    enddate             DATE                        NOT NULL,
    correct_points      INTEGER                     NOT NULL,
    penalty_points      INTEGER                     NOT NULL,
    seed                INTEGER         DEFAULT 0   NOT NULL,
    score_method        VARCHAR2(30)                NOT NULL,
    maximum_attempts    INTEGER         DEFAULT 1   NOT NULL,
    question_count      INTEGER         DEFAULT 1   NOT NULL,
    PRIMARY KEY (eid)
);

CREATE SEQUENCE exercise_ids START WITH 1000 INCREMENT BY 1;
SELECT exercise_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE Question (
    qname           VARCHAR2(64)                NOT NULL,
    text            VARCHAR2(1000)              NOT NULL,
    difficulty      INTEGER                     NOT NULL,
    hint            VARCHAR2(1000)  DEFAULT '',
    correct_points  INTEGER                     NOT NULL,
    penalty_points  INTEGER                     NOT NULL,
    explanation     VARCHAR2(1000)  DEFAULT '',
    PRIMARY KEY (qname)
);

CREATE TABLE ExerciseQuestion (
    eid             INTEGER                     NOT NULL,
    qname           VARCHAR2(64)                NOT NULL,
    PRIMARY KEY (eid, qname),
    FOREIGN KEY (eid) REFERENCES Exercise,
    FOREIGN KEY (qname) REFERENCES Question
);

CREATE TABLE Answer (
    qname           VARCHAR2(64)                NOT NULL,
    ansid           INTEGER                     NOT NULL,
    text            VARCHAR2(1000)              NOT NULL,
    correct         INTEGER                     NOT NULL,
    explanation     VARCHAR2(1000)  DEFAULT '',
    hint            VARCHAR2(1000)  DEFAULT '',
    PRIMARY KEY(ansid),
    FOREIGN KEY (qname) REFERENCES Question,
    CONSTRAINT answer_correct_bool CHECK (correct IN (0, 1))
);

CREATE SEQUENCE answer_ids START WITH 1000 INCREMENT BY 1;
SELECT answer_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE Attempt (
    attid           INTEGER         NOT NULL,
    eid             INTEGER         NOT NULL,
    sid             VARCHAR2(64)    NOT NULL,
    attnum          INTEGER         NOT NULL,
    submittime      DATE, /* if this is NULL, attempt has not been submitted */
    points          INTEGER         DEFAULT 0,
    PRIMARY KEY (attid),
    UNIQUE (eid, sid, attnum),
    CONSTRAINT attempt_attnum_constraint CHECK (0 < attnum),
    FOREIGN KEY (eid) REFERENCES Exercise,
    FOREIGN KEY (sid) REFERENCES Student
);

CREATE SEQUENCE attempt_ids START WITH 1000 INCREMENT BY 1;
SELECT attempt_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE AttemptQuestion (
    attid           INTEGER                         NOT NULL,
    qposition       INTEGER                         NOT NULL,
    qname           VARCHAR2(64)                    NOT NULL,
    chosen_answer_pos   INTEGER,  /* if this is NULL, no answer has been given yet */
    justification   VARCHAR2(1000)      DEFAULT '',
    points          INTEGER         DEFAULT 0,
    PRIMARY KEY (attid, qposition),
    CONSTRAINT attquestion_qpos_constraint CHECK (0 < qposition),
    CONSTRAINT attquestion_cap_constraint CHECK ((0 < chosen_answer_pos) OR (chosen_answer_pos IS NULL)),
    FOREIGN KEY (attid) REFERENCES Attempt,
    FOREIGN KEY (qname) REFERENCES Question
);

CREATE TABLE AttemptAnswer (
    attid           INTEGER         NOT NULL,
    qposition       INTEGER         NOT NULL,
    aposition       INTEGER         NOT NULL,
    ansid           INTEGER         NOT NULL,
    PRIMARY KEY (attid, qposition, aposition),
    CONSTRAINT attanswer_apos_constraint CHECK (0 < aposition),
    FOREIGN KEY (attid, qposition) REFERENCES AttemptQuestion,
    FOREIGN KEY (ansid) REFERENCES Answer
);

/* ALTER TABLE AttemptQuestion
    ADD CONSTRAINT fk_attemptans 
        FOREIGN KEY (attid, qposition, chosen_answer_pos) REFERENCES AttemptAnswer;
*/
EXIT;

