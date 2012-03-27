
CREATE TABLE UserInfo (
    userid INTEGER,
    username VARCHAR2(128) NOT NULL,
    password VARCHAR2(128) NOT NULL,
    role VARCHAR2(32) NOT NULL,
    PRIMARY KEY (userid),
    CONSTRAINT userinfo_role_enum CHECK (role IN ('prof','student')),
    UNIQUE (username)
);

CREATE SEQUENCE userinfo_ids START WITH 1 INCREMENT BY 1;

CREATE TABLE Student (
    sid VARCHAR2(64) NOT NULL,
    userid INTEGER,
    PRIMARY KEY (sid),
    FOREIGN KEY (userid) REFERENCES UserInfo
);

CREATE TABLE Professor (
    pid INTEGER,
    userid INTEGER,
    PRIMARY KEY (pid),
    FOREIGN KEY (userid) REFERENCES UserInfo
);

CREATE SEQUENCE professor_ids START WITH 1 INCREMENT BY 1;

CREATE TABLE TA (
    taid INTEGER,
    userid INTEGER,
    PRIMARY KEY (taid),
    FOREIGN KEY (userid) REFERENCES UserInfo
);

CREATE SEQUENCE ta_ids START WITH 1 INCREMENT BY 1;

CREATE TABLE Topic (
    tid INTEGER,
    text VARCHAR2(1000),
    PRIMARY KEY (tid)
);

CREATE SEQUENCE topic_ids START WITH 1 INCREMENT BY 1;

CREATE TABLE Course (
    cid VARCHAR2(32) NOT NULL,
    cname VARCHAR2(1000),
    startdate TIMESTAMP,
    enddate TIMESTAMP,
    pid INTEGER,
    PRIMARY KEY (cid),
    FOREIGN KEY (pid) REFERENCES Professor
);

CREATE TABLE Enrolled (
    cid VARCHAR2(32),
    sid VARCHAR2(64),
    PRIMARY KEY (cid, sid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (sid) REFERENCES Student
);

CREATE TABLE Assisting (
    cid VARCHAR2(32),
    taid INTEGER,
    PRIMARY KEY (cid, taid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (taid) REFERENCES TA
);

CREATE TABLE TopicPerCourse (
    cid VARCHAR2(32),
    tid INTEGER,
    PRIMARY KEY (cid, tid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (tid) REFERENCES Topic
);

CREATE TABLE Exercise (
    eid             INTEGER     NOT NULL,
    startdate       TIMESTAMP   NOT NULL,
    enddate         TIMESTAMP   NOT NULL,
    correct_points  INTEGER     NOT NULL,
    penalty_points  INTEGER     NOT NULL,
    seed            INTEGER     NOT NULL,
    score_method    VARCHAR2(30)    NOT NULL,
    PRIMARY KEY (eid)
);

CREATE SEQUENCE exercise_ids START WITH 1 INCREMENT BY 1;

CREATE TABLE Question (
    qname           VARCHAR2(64)        NOT NULL,
    text            VARCHAR2(1000)  NOT NULL,
    difficulty      INTEGER         NOT NULL,
    hint            VARCHAR2(1000)  DEFAULT ''  NOT NULL,
    correct_points  INTEGER         NOT NULL,
    penalty_points  INTEGER         NOT NULL,
    explanation     VARCHAR2(1000)  DEFAULT ''  NOT NULL,
    PRIMARY KEY (qname)
);

CREATE TABLE ExerciseQuestion (
    eid             INTEGER         NOT NULL,
    qname           VARCHAR2(64)        NOT NULL,
    PRIMARY KEY (eid, qname),
    FOREIGN KEY (eid) REFERENCES Exercise,
    FOREIGN KEY (qname) REFERENCES Question
);

CREATE TABLE Answer (
    qname           VARCHAR2(64)        NOT NULL,
    ansid           INTEGER         NOT NULL,
    text            VARCHAR2(1000)  NOT NULL,
    correct         INTEGER         NOT NULL,
    explanation     VARCHAR2(1000)  DEFAULT ''  NOT NULL,
    PRIMARY KEY(ansid),
    FOREIGN KEY (qname) REFERENCES Question
);

CREATE SEQUENCE answer_ids START WITH 1 INCREMENT BY 1;

CREATE TABLE Attempt (
    attid           INTEGER         NOT NULL,
    eid             INTEGER         NOT NULL,
    sid             VARCHAR2(64)        NOT NULL,
    attnum          INTEGER         NOT NULL,
    /* starttime TIMESTAMP, */
    submittime      TIMESTAMP, /* if this is NULL, attempt has not been submitted */
    PRIMARY KEY (attid),
    UNIQUE (eid, sid, attnum),
    FOREIGN KEY (eid) REFERENCES Exercise,
    FOREIGN KEY (sid) REFERENCES Student
);

CREATE SEQUENCE attempt_ids START WITH 1 INCREMENT BY 1;

CREATE TABLE AttemptQuestion (
    attid           INTEGER         NOT NULL,
    qposition       INTEGER         NOT NULL,
    qname           VARCHAR2(64)        NOT NULL,
    chosen_answer_pos   INTEGER,  /* if this is NULL, no answer has been given yet */
    justification   VARCHAR2(1000)   DEFAULT ''   NOT NULL,
    PRIMARY KEY (attid, qposition),
    FOREIGN KEY (attid) REFERENCES Attempt,
    FOREIGN KEY (qname) REFERENCES Question
);

CREATE TABLE AttemptAnswer (
    attid           INTEGER         NOT NULL,
    qposition       INTEGER         NOT NULL,
    aposition       INTEGER         NOT NULL,
    ansid           INTEGER         NOT NULL,
    PRIMARY KEY (attid, qposition, aposition),
    FOREIGN KEY (attid, qposition) REFERENCES AttemptQuestion,
    FOREIGN KEY (ansid) REFERENCES Answer
);

ALTER TABLE AttemptQuestion
    ADD CONSTRAINT fk_attemptans 
        FOREIGN KEY (attid, qposition, chosen_answer_pos) REFERENCES AttemptAnswer;



