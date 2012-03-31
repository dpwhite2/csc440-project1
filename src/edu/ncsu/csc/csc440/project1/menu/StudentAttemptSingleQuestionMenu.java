package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.db.AttemptAnswerDAO;
import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.db.QuestionDAO;
import edu.ncsu.csc.csc440.project1.objs.AttemptAnswer;

public class StudentAttemptSingleQuestionMenu extends Menu {
    
    class AttemptQuestionMenuChoice extends MenuChoice {
        public int apos = -1;
        public AttemptQuestionMenuChoice(String shortcut, String description, int apos) {
            super(shortcut, description);
            this.apos = apos;
        }
    }
    
    private int attid;
    private String sid;
    private String cid;
    private int qposition;
    private int nQuestions;
    
    public StudentAttemptSingleQuestionMenu(String sid, String cid, int attid, int qpos) throws Exception {
        this.attid = attid;
        this.sid = sid;
        this.cid = cid;
        this.qposition = qpos;
        this.nQuestions = getNumberOfQuestions();
    }
    
    public String headerMsg() throws Exception {
        return getQuestionText();
    }
    
    private int getNumberOfQuestions() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM AttemptQuestion AQ WHERE AQ.attid=?");
            stmt.setInt(1, attid);
            ResultSet rs = stmt.executeQuery();
            // no need to check rs.next() -- count() will always return one row
            rs.next();
            return rs.getInt(1);
        } finally {
            conn.close();
        }
    }
    
    private String getQuestionText() throws Exception {
        return QuestionDAO.getQuestionByAttempt(attid, qposition).getText();
    }
    
    private ArrayList<AttemptAnswer> getAnswers() throws Exception {
        return AttemptAnswerDAO.getAttemptAnswersAndText(attid, qposition);
    }

    @Override
    public MenuChoice[] getChoices() throws Exception {
        // one entry for each answer
        ArrayList<AttemptAnswer> answers = getAnswers();
        int xtra = 3;
        if (this.qposition == 1) {
            // no "previous" choice
            xtra -= 1;
        }
        if (this.qposition == this.nQuestions) {
            // no "next" choice
            xtra -= 1;
        }
        MenuChoice[] choices = new MenuChoice[answers.size() + xtra];
        for (int i=0; i<answers.size(); i++) {
            int apos = i+1;
            AttemptAnswer ans = answers.get(i);
            String shortcut = String.valueOf(apos);
            String label = ans.getText();
            choices[i] = new AttemptQuestionMenuChoice(shortcut, label, apos);
        }
        // one entry to go back to homework menu
        choices[answers.size()] = new AttemptQuestionMenuChoice("X","Back to Homework Menu",-1);
        // one entry to go to previous question (unless at end of exercise)
        if (this.qposition != 1) {
            choices[answers.size() + 1] = new AttemptQuestionMenuChoice("P","Previous Question",-1);
        }
        // one entry to go to next question (unless at beginning of exercise)
        if (this.qposition < this.nQuestions) {
            choices[choices.length-1] = new AttemptQuestionMenuChoice("N","Next Question",-1);
        }
        return choices;
    }
    
    private void saveSelectedAnswer(int apos) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE AttemptQuestion SET chosen_answer_pos=? WHERE attid=? AND qposition=?");
            stmt.setInt(1, apos);
            stmt.setInt(2, attid);
            stmt.setInt(3, qposition);
            stmt.executeUpdate();
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean onChoice(MenuChoice choice_) throws Exception {
        AttemptQuestionMenuChoice choice = (AttemptQuestionMenuChoice)choice_;
        if (choice.apos == -1) {
            
            if (choice.shortcut.equals("X")) {
             // if choice is "X": back
                return false;
            } else if (choice.shortcut.equals("N")) {
                // if choice is "N": next question
                this.qposition += 1;
                return true;
            } else if (choice.shortcut.equals("P")) {
                // if choice is "P": previous question
                this.qposition -= 1;
                return true;
            } else {
                throw new RuntimeException("Should never get here.");
            }
        } else {
            // save answer
            System.out.printf("DBG: Selected question position = %d\n", this.qposition);
            System.out.printf("DBG: Selected answer position =   %d\n", choice.apos);
            saveSelectedAnswer(choice.apos);
            if (this.qposition < this.nQuestions) {
                // if not at end, go to next question
                this.qposition += 1;
                return true;
            } else {
                // if at the end, go back to homework menu
                return false;
            }
        }
    }

}
