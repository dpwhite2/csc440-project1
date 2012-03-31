package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import edu.ncsu.csc.csc440.project1.db.AttemptAnswerDAO;
import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.db.QuestionDAO;
import edu.ncsu.csc.csc440.project1.menu.StudentAttemptSingleQuestionMenu.AttemptQuestionMenuChoice;
import edu.ncsu.csc.csc440.project1.objs.AttemptAnswer;
import edu.ncsu.csc.csc440.project1.objs.AttemptQuestion;
import edu.ncsu.csc.csc440.project1.objs.Exercise;
import edu.ncsu.csc.csc440.project1.objs.Question;

public class StudentViewSingleQuestionMenu extends Menu {
    
    class ViewQuestionMenuChoice extends MenuChoice {
        public int apos = -1;
        public ViewQuestionMenuChoice(String shortcut, String description, int apos) {
            super(shortcut, description);
            this.apos = apos;
        }
    }
    
    private int attid;
    private String sid;
    private String cid;
    private int qposition;
    private int nQuestions;

    public StudentViewSingleQuestionMenu(String sid, String cid, int attid,
            int qpos) throws Exception {
        this.attid = attid;
        this.sid = sid;
        this.cid = cid;
        this.qposition = qpos;
        this.nQuestions = getNumberOfQuestions();
    }
    
    public String headerMsg() throws Exception {
        return getQuestionText();
    }
    
    private Exercise getExercise() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT E.* FROM Exercise E, Attempt A WHERE A.attid=? AND A.eid=E.eid");
            stmt.setInt(1, attid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Could not find Exercise.");
            }
            return new Exercise(rs);
        } finally {
            conn.close();
        }
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
    
    private AttemptQuestion getAttemptQuestion() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT AQ.* FROM AttemptQuestion AQ WHERE AQ.attid=? AND AQ.qposition=?");
            stmt.setInt(1, attid);
            stmt.setInt(2, qposition);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Could not find AttemptQuestion");
            }
            return new AttemptQuestion(rs);
        } finally {
            conn.close();
        }
    }
    
    private String getExplanation() throws Exception {
        return QuestionDAO.getQuestionByAttempt(attid, qposition).getExplanation();
    }
    private String getHint() throws Exception {
        return QuestionDAO.getQuestionByAttempt(attid, qposition).getHint();
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
        boolean is_correct = false;
        AttemptQuestion aq = getAttemptQuestion();
        for (AttemptAnswer ans: answers) {
            if (ans.getAposition() == aq.getChosenAnswerPosition() && ans.isCorrect()) {
                is_correct = true;
            }
        }
        
        ArrayList<MenuChoice> choices = new ArrayList<MenuChoice>();
        //MenuChoice[] choices = new MenuChoice[answers.size() + xtra];
        for (int i=0; i<answers.size(); i++) {
            int apos = i+1;
            AttemptAnswer ans = answers.get(i);
            String shortcut = String.valueOf(apos);
            String label = null;
            if (ans.isCorrect() && aq.getChosenAnswerPosition()==ans.getAposition()) {
                label = String.format("[selected](correct) %s", ans.getText());
            } else if (ans.isCorrect()) {
                label = String.format("(correct) %s", ans.getText());
            } else if (aq.getChosenAnswerPosition()==ans.getAposition()) {
                label = String.format("[selected] %s", ans.getText());
            } else {
                label = ans.getText();
            }
            choices.add(new ViewQuestionMenuChoice(shortcut, label, apos));
        }
        Exercise ex = getExercise();
        if (!is_correct) {
            if ((new Date()).after(ex.getEndDate())) {
                choices.add(new ViewQuestionMenuChoice("Explanation", getExplanation(),-1));
            } else {
                choices.add(new ViewQuestionMenuChoice("Hint", getHint(),-1));
            }
        }
        // one entry to go back to homework menu
        choices.add(new ViewQuestionMenuChoice("X","Back to Homework Menu",-1));
        // one entry to go to previous question (unless at end of exercise)
        if (this.qposition != 1) {
            choices.add(new ViewQuestionMenuChoice("P","Previous Question",-1));
        }
        // one entry to go to next question (unless at beginning of exercise)
        if (this.qposition < this.nQuestions) {
            choices.add(new ViewQuestionMenuChoice("N","Next Question",-1));
        }
        return choices.toArray(new MenuChoice[0]);
    }

    @Override
    public boolean onChoice(MenuChoice choice_) throws Exception {
        ViewQuestionMenuChoice choice = (ViewQuestionMenuChoice)choice_;
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
