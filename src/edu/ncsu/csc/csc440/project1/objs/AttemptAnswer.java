package edu.ncsu.csc.csc440.project1.objs;

import java.sql.ResultSet;

public class AttemptAnswer {
    private int attid;
    private int qposition;
    private int aposition;
    private int ansid;
    private String text = "";

    public AttemptAnswer() {
        this.attid = -1;
        this.qposition = -1;
        this.aposition = -1;
        this.ansid = -1;
    }
    
    public AttemptAnswer(int attid, int qposition, int aposition, int ansid) {
        this.attid = attid;
        this.qposition = qposition;
        this.aposition = aposition;
        this.ansid = ansid;
    }
    
    public AttemptAnswer(ResultSet rs) throws Exception {
        this.attid = rs.getInt("attid");
        this.qposition = rs.getInt("qposition");
        this.aposition = rs.getInt("aposition");
        this.ansid = rs.getInt("ansid");
    }
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public int getAttid() {
        return attid;
    }
    public int getQposition() {
        return qposition;
    }
    public int getAposition() {
        return aposition;
    }
    public int getAnsid() {
        return ansid;
    }
    
}
