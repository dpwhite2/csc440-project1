
/* UserInfo contains information common to all users (whether Students or 
   Professors). Basically, that is the username and password. */
CREATE TABLE UserInfo (
    userid INTEGER,
    username VARCHAR2(128)      NOT NULL,
    password VARCHAR2(128)      NOT NULL,
    role VARCHAR2(32)           NOT NULL,
    PRIMARY KEY (userid),
    CONSTRAINT userinfo_role_enum CHECK (role IN ('prof','student','ta')),
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
    FOREIGN KEY (pid) REFERENCES Professor,
    CONSTRAINT course_dates_constraint CHECK (startdate <= enddate)
);

/* Many-to-many relationship between Courses and Students. */
CREATE TABLE Enrolled (
    cid VARCHAR2(32)            NOT NULL,
    sid VARCHAR2(64)            NOT NULL,
    PRIMARY KEY (cid, sid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (sid) REFERENCES Student
);

/* Many-to-many relationship between Courses and TAs. */
CREATE TABLE Assisting (
    cid VARCHAR2(32)            NOT NULL,
    taid INTEGER                NOT NULL,
    PRIMARY KEY (cid, taid),
    FOREIGN KEY (cid) REFERENCES Course,
    FOREIGN KEY (taid) REFERENCES TA
);

/* Many-to-many relationship between Courses and Topics. */
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
    correct_points      INTEGER                     NOT NULL, /* points given for correct answers */
    penalty_points      INTEGER                     NOT NULL, /* points taken for incorrect answers */
    seed                INTEGER         DEFAULT 0   NOT NULL,
    score_method        VARCHAR2(30)                NOT NULL, 
    maximum_attempts    INTEGER         DEFAULT 1   NOT NULL,
    question_count      INTEGER         DEFAULT 1   NOT NULL,
    PRIMARY KEY (eid),
    CONSTRAINT exercise_cpts_constraint CHECK (correct_points >= 0),
    CONSTRAINT exercise_ppts_constraint CHECK (penalty_points >= 0),
    CONSTRAINT exercise_smeth_constraint CHECK (score_method IN ('first attempt','last attempt','max','average')),
    CONSTRAINT exercise_maxatt_constraint CHECK (maximum_attempts > 0),
    CONSTRAINT exercise_qcount_constraint CHECK (question_count > 0),
    CONSTRAINT exercise_dates_constraint CHECK (startdate <= enddate)
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

/* Many-to-many relationship between Exercises and Questions. */
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
    /* Limit 'correct' to True or False. */
    CONSTRAINT answer_correct_bool CHECK (correct IN (0, 1))
);

CREATE SEQUENCE answer_ids START WITH 1000 INCREMENT BY 1;
SELECT answer_ids.nextval FROM DUAL;  -- initialize sequence

CREATE TABLE Attempt (
    attid           INTEGER         NOT NULL,
    eid             INTEGER         NOT NULL,
    sid             VARCHAR2(64)    NOT NULL,
    attnum          INTEGER         NOT NULL,  /* Indicates this is the Nth attempt. */
    submittime      DATE, /* if this is NULL, attempt has not been submitted */
    points          INTEGER         DEFAULT 0,
    PRIMARY KEY (attid),
    UNIQUE (eid, sid, attnum),
    /* attnum must be greater than 0. */
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
    points          INTEGER             DEFAULT 0,
    PRIMARY KEY (attid, qposition),
    /* qposition must be greater than 0. */
    CONSTRAINT attquestion_qpos_constraint CHECK (0 < qposition),
    /* chosen_answer_pos must be NULL or greater than 0. */
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
    /* aposition must be greater than 0. */
    CONSTRAINT attanswer_apos_constraint CHECK (0 < aposition),
    FOREIGN KEY (attid, qposition) REFERENCES AttemptQuestion,
    FOREIGN KEY (ansid) REFERENCES Answer
);

/* ALTER TABLE AttemptQuestion
    ADD CONSTRAINT fk_attemptans 
        FOREIGN KEY (attid, qposition, chosen_answer_pos) REFERENCES AttemptAnswer;
*/
EXIT;

