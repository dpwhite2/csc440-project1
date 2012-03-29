package edu.ncsu.csc.csc440.project1.objs;

import java.sql.ResultSet;

public class AttemptQuestion {
    private int attid;
    private int qposition;
    private String qname;
    private int chosenAnswerPosition;
    private String justification;
    
    public AttemptQuestion() {
        this.attid = -1;
        this.qposition = -1;
        this.qname = "";
        this.chosenAnswerPosition = -1;
        this.justification = "";
    }
    
    public AttemptQuestion(int attid, int qposition, String qname,
            int chosenAnswerPosition, String justification) {
        this.attid = attid;
        this.qposition = qposition;
        this.qname = qname;
        this.chosenAnswerPosition = chosenAnswerPosition;
        this.justification = justification;
    }
    
    public AttemptQuestion(ResultSet rs) throws Exception {
        this.attid = rs.getInt("attid");
        this.qposition = rs.getInt("qposition");
        this.qname = rs.getString("qname");
        this.chosenAnswerPosition = rs.getInt("chosen_answer_pos");
        this.justification = rs.getString("justification");
    }

    public int getAttid() {
        return attid;
    }
    public int getQposition() {
        return qposition;
    }
    public String getQname() {
        return qname;
    }
    public int getChosenAnswerPosition() {
        return chosenAnswerPosition;
    }
    public String getJustification() {
        return justification;
    }
}
