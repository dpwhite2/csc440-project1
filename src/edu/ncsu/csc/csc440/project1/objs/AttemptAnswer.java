package edu.ncsu.csc.csc440.project1.objs;

public class AttemptAnswer {
    private int attid;
    private int qposition;
    private int aposition;
    private int ansid;
    
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
