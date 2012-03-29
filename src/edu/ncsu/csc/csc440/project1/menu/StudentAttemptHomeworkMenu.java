package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.menu.StudentAttemptHomeworkSelectMenu.ExerciseMenuChoice;
import edu.ncsu.csc.csc440.project1.objs.Attempt;
import edu.ncsu.csc.csc440.project1.objs.AttemptFactory;
import edu.ncsu.csc.csc440.project1.objs.AttemptQuestion;

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
    
    private ArrayList<AttemptQuestion> getQuestions() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AttemptQuestion AQ WHERE AQ.attid=? ORDER BY AQ.qposition ASC");
            stmt.setInt(1, attid);
            ResultSet rs = stmt.executeQuery();
            ArrayList<AttemptQuestion> questions = new ArrayList<AttemptQuestion>();
            while (rs.next()) {
                questions.add(new AttemptQuestion(rs));
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
        ArrayList<AttemptQuestion> questions = getQuestions();
        MenuChoice[] choices = new MenuChoice[questions.size() + 2];
        for (int i=0; i<questions.size(); i++) {
            AttemptQuestion q = questions.get(i);
            String shortcut = String.valueOf(i+1);
            String label = "Question " + String.valueOf(i+1);
            choices[i] = new AttemptHomeworkMenuChoice(shortcut, label, q.getAttid(), q.getQposition());
        }
        choices[questions.size()] = new MenuChoice("S", "Submit");
        choices[questions.size() + 1] = new MenuChoice("X", "Back");
        return choices;
    }
    
    private void submitAttempt() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Attempt SET submittime=? WHERE attid=?");
            stmt.setDate(1, new Date(System.currentTimeMillis()));
            stmt.setInt(2, attid);
            int updates = stmt.executeUpdate();
            System.out.printf("DBG: Updated %d rows.\n", updates);
        } finally {
            conn.close();
        }
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
