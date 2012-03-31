package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;

import edu.ncsu.csc.csc440.project1.db.AttemptAnswerDAO;
import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.db.ExerciseDAO;
import edu.ncsu.csc.csc440.project1.objs.AttemptAnswer;
import edu.ncsu.csc.csc440.project1.objs.AttemptQuestion;
import edu.ncsu.csc.csc440.project1.objs.Exercise;

public class StudentAttemptHomeworkMenu extends Menu {

    class AttemptHomeworkMenuChoice extends MenuChoice {
        public int attid = -1;
        public int qpos = -1;
        public AttemptHomeworkMenuChoice(String shortcut, String description, int attid, int qpos) {
            super(shortcut, description);
            this.attid = attid;
            this.qpos = qpos;
        }
    }
    
    private String sid;
    private String cid;
    private int attid;
    
    public StudentAttemptHomeworkMenu(String sid, String cid, int attid) {
        this.sid = sid;
        this.cid = cid;
        this.attid = attid;
    }
    
    public String headerMsg() {
        return "Attempt Homework";
    }
    
    private ArrayList<AttemptQuestion> getAttemptQuestions() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT AQ.*, Q.text FROM AttemptQuestion AQ, Question Q WHERE AQ.attid=? AND AQ.qname=Q.qname ORDER BY AQ.qposition ASC");
            stmt.setInt(1, attid);
            ResultSet rs = stmt.executeQuery();
            ArrayList<AttemptQuestion> questions = new ArrayList<AttemptQuestion>();
            while (rs.next()) {
                AttemptQuestion aq = new AttemptQuestion(rs);
                aq.setQuestionText(rs.getString("text"));
                questions.add(aq);
            }
            return questions;
        } finally {
            conn.close();
        }
    }

    @Override
    public MenuChoice[] getChoices() throws Exception {
        System.out.printf("DBG: getChoices(): attid = %d\n",attid);
        // Get all questions
        ArrayList<AttemptQuestion> questions = getAttemptQuestions();
        MenuChoice[] choices = new MenuChoice[questions.size() + 2];
        for (int i=0; i<questions.size(); i++) {
            AttemptQuestion q = questions.get(i);
            String shortcut = String.valueOf(i+1);
            String label = q.getText();
            choices[i] = new AttemptHomeworkMenuChoice(shortcut, label, q.getAttid(), q.getQposition());
        }
        choices[questions.size()] = new MenuChoice("S", "Submit");
        choices[questions.size() + 1] = new MenuChoice("X", "Back");
        return choices;
    }
    
    private Exercise getExercise() throws Exception {
        return ExerciseDAO.getExerciseForAttempt(attid);
    }
    
    private ArrayList<AttemptAnswer> getAttemptAnswers(int qposition) throws Exception {
        return AttemptAnswerDAO.getAttemptAnswersAndText(attid, qposition);
    }
    
    private void writeAttemptQuestionScore(int qposition, int score) throws Exception {
        System.out.printf("DBG: points on question %d: %d\n", qposition, score);
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE AttemptQuestion SET points=? WHERE attid=? AND qposition=?");
            stmt.setInt(1, score);
            stmt.setInt(2, attid);
            stmt.setInt(3, qposition);
            stmt.executeUpdate();
        } finally {
            conn.close();
        }
    }
    
    private void finishAttemptScore() throws Exception {
        Connection conn = null;
        try {
            // sum scores
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT sum(AQ.points) FROM AttemptQuestion AQ WHERE AQ.attid=?");
            stmt.setInt(1, attid);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int points = rs.getInt(1);
            System.out.printf("DBG: total points: %d\n", points);
            // save total and submission time
            stmt = conn.prepareStatement("UPDATE Attempt SET points=?, submittime=? WHERE attid=?");
            stmt.setInt(1, points);
            stmt.setDate(2, new Date(System.currentTimeMillis()));
            stmt.setInt(3, attid);
            int updates = stmt.executeUpdate();
            System.out.printf("DBG: Updated %d rows.\n", updates);
        } finally {
            conn.close();
        }
    }
    
    private void submitAttempt() throws Exception {
        Exercise ex = getExercise();
        ArrayList<AttemptQuestion> questions = getAttemptQuestions();
        for (AttemptQuestion q: questions) {
            ArrayList<AttemptAnswer> answers = getAttemptAnswers(q.getQposition());
            int apos = q.getChosenAnswerPosition();
            int points = 0;
            if (answers.get(apos-1).isCorrect()) {
                points = ex.getCorrectPoints();
            } else {
                points = -ex.getPenaltyPoints();
            }
            writeAttemptQuestionScore(q.getQposition(), points);
        }
        finishAttemptScore();
    }
    
    @Override
    public boolean onChoice(MenuChoice choice_) throws Exception {
        if (choice_.shortcut.equals("X")) {
            return false;
        } else if (choice_.shortcut.equals("S")) {
            submitAttempt();
            return false;
        } else {
            // assume choice refers to valid question
            AttemptHomeworkMenuChoice choice = (AttemptHomeworkMenuChoice)choice_;
            int attid = choice.attid;
            int qpos = choice.qpos;
            StudentAttemptSingleQuestionMenu menu = new StudentAttemptSingleQuestionMenu(sid, cid, attid, qpos);
            menu.menuLoop();
            return true;
        } 
    }
}
