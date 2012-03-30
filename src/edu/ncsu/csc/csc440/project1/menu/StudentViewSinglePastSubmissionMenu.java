package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.AttemptQuestion;

public class StudentViewSinglePastSubmissionMenu extends Menu {
    
    class ViewHomeworkMenuChoice extends MenuChoice {
        public int attid = -1;
        public int qpos = -1;
        public ViewHomeworkMenuChoice(String shortcut, String description, int attid, int qpos) {
            super(shortcut, description);
            this.attid = attid;
            this.qpos = qpos;
        }
    }
    
    private String sid;
    private String cid;
    private int attid;

    public StudentViewSinglePastSubmissionMenu(String sid, String cid, int attid) {
        this.sid = sid;
        this.cid = cid;
        this.attid = attid;
    }
    
    private ArrayList<AttemptQuestion> getQuestions() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT AQ.* FROM AttemptQuestion AQ WHERE AQ.attid=? ORDER BY AQ.qposition ASC");
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
        // get questions
        ArrayList<AttemptQuestion> questions = getQuestions();
        MenuChoice[] choices = new MenuChoice[questions.size() + 1];
        for (int i=0; i<questions.size(); i++) {
            AttemptQuestion q = questions.get(i);
            String shortcut = String.valueOf(i+1);
            String label = "Question " + String.valueOf(i+1);
            choices[i] = new ViewHomeworkMenuChoice(shortcut, label, q.getAttid(), q.getQposition());
        }
        choices[questions.size()] = new MenuChoice("X", "Back");
        return choices;
    }

    @Override
    public boolean onChoice(MenuChoice choice_) throws Exception {
        if (choice_.shortcut.equals("X")) {
            return false;
        } else {
            // assume choice refers to valid question
            ViewHomeworkMenuChoice choice = (ViewHomeworkMenuChoice)choice_;
            int attid = choice.attid;
            int qpos = choice.qpos;
            StudentViewSingleQuestionMenu menu = new StudentViewSingleQuestionMenu(sid, cid, attid, qpos);
            menu.menuLoop();
            return true;
        } 
    }

}
