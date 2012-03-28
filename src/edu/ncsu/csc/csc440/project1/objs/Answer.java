package edu.ncsu.csc.csc440.project1.objs;

import java.sql.ResultSet;

public class Answer {
    private String qname;
    private int ansid;
    private String text;
    private boolean correct;
    private String explanation;
    private String hint;
    
    public Answer() {
        this.qname = "";
        this.ansid = -1;
        this.text = "";
        this.correct = false;
        this.explanation = "";
        this.hint = "";
    }
    
    public Answer(String qname, int ansid, String text, boolean correct,
            String explanation, String hint) {
        this.qname = qname;
        this.ansid = ansid;
        this.text = text;
        this.correct = correct;
        this.explanation = explanation;
        this.hint = hint;
    }
    
    public Answer(ResultSet rs) throws Exception{
        this.qname = rs.getString("qname");
        this.ansid = rs.getInt("ansid");
        this.text = rs.getString("text");
        this.correct = rs.getBoolean("correct");
        this.explanation = rs.getString("explanation");
        this.hint = rs.getString("hint");
    }
    
    public String getQname() {
        return qname;
    }
    public int getAnsid() {
        return ansid;
    }
    public String getText() {
        return text;
    }
    public boolean isCorrect() {
        return correct;
    }
    public String getExplanation() {
        return explanation;
    }
    public String getHint() {
        return hint;
    }
    
    
}
