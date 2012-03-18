
CREATE TABLE Student (
    sid CHAR(64),
    PRIMARY KEY (sid)
);

CREATE TABLE Professor (
    pid INTEGER,
    PRIMARY KEY (pid)
);

CREATE TABLE TA (
    taid INTEGER,
    PRIMARY KEY (taid)
);

CREATE TABLE Topic (
    tid INTEGER,
    text VARCHAR2(1000),
    PRIMARY KEY (tid)
);

CREATE TABLE Course (
    cid CHAR(32),
    cname VARCHAR(1000),
    startdate TIMESTAMP,
    enddate TIMESTAMP,
    pid INTEGER,
    PRIMARY KEY (cid),
    FOREIGN KEY (pid) REFERENCES Professor
);

CREATE TABLE Enrolled (
    cid CHAR(32),
    sid CHAR(64),
    PRIMARY KEY (cid, sid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (sid) REFERENCES Student
);

CREATE TABLE Assisting (
    cid CHAR(32),
    taid INTEGER,
    PRIMARY KEY (cid, taid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (taid) REFERENCES TA
);

CREATE TABLE TopicPerCourse (
    cid CHAR(32),
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
    score_method    CHAR(30)    NOT NULL,
    PRIMARY KEY (eid)
);

CREATE TABLE Question (
    qname           CHAR(64)        NOT NULL,
    text            VARCHAR2(1000)  NOT NULL,
    difficulty      INTEGER         NOT NULL,
    hint            VARCHAR2(1000)  NOT NULL    DEFAULT '',
    correct_points  INTEGER         NOT NULL,
    penalty_points  INTEGER         NOT NULL,
    explanation     VARCHAR2(1000)  NOT NULL    DEFAULT '',
    PRIMARY KEY (qname)
);

CREATE TABLE ExerciseQuestion (
    eid             INTEGER         NOT NULL,
    qname           CHAR(64)        NOT NULL,
    PRIMARY KEY (eid, qname),
    FOREIGN KEY (eid) REFERENCES Exercise,
    FOREIGN KEY (qname) REFERENCES Question
);

CREATE TABLE Answer (
    qname           CHAR(64)        NOT NULL,
    ansid           INTEGER         NOT NULL,
    text            VARCHAR2(1000)  NOT NULL,
    correct         BOOLEAN         NOT NULL,
    explanation     VARCHAR2(1000)  NOT NULL    DEFAULT '',
    PRIMARY KEY(ansid),
    FOREIGN KEY (qname) REFERENCES Question
);

CREATE TABLE Attempt (
    attid           INTEGER         NOT NULL,
    eid             INTEGER         NOT NULL,
    sid             CHAR(64)        NOT NULL,
    attnum          INTEGER         NOT NULL,
    /* starttime TIMESTAMP, */
    submittime      TIMESTAMP, /* if this is NULL, attempt has not been submitted */
    PRIMARY KEY (attid),
    UNIQUE (eid, sid, attnum),
    FOREIGN KEY (eid) REFERENCES Exercise,
    FOREIGN KEY (sid) REFERENCES Student
);

CREATE TABLE AttemptQuestion (
    attid           INTEGER         NOT NULL,
    qposition       INTEGER         NOT NULL,
    qname           CHAR(64)        NOT NULL,
    chosen_answer_pos   INTEGER,  /* if this is NULL, no answer has been given yet */
    justification   VARCHAR(1000)   NOT NULL    DEFAULT '',
    PRIMARY KEY (attid, qposition),
    FOREIGN KEY (attid) REFERENCES Attempt,
    FOREIGN KEY (qname) REFERENCES Question,
    FOREIGN KEY (attid, qposition, chosen_answer_pos) REFERENCES AttemptAnswer
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





