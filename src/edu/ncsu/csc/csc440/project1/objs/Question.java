package edu.ncsu.csc.csc440.project1.objs;

import java.sql.ResultSet;

public class Question {
	
	private String qname;
	private String text;
	private int difficulty;
	private String hint;
	private int correctPoints;
	private int penaltyPoints;
	private String explanation;
	
	public Question() {
		this.qname = "";
		this.text = "";
		this.difficulty = -1;
		this.hint = "";
		this.correctPoints = -1;
		this.penaltyPoints = -1;
		this.explanation = "";
	}
	
	public Question(String qname, String text, int difficulty, String hint,
			int correctPoints, int penaltyPoints, String explanation) {
		super();
		this.qname = qname;
		this.text = text;
		this.difficulty = difficulty;
		this.hint = hint;
		this.correctPoints = correctPoints;
		this.penaltyPoints = penaltyPoints;
		this.explanation = explanation;
	}

	public Question(ResultSet rs) throws Exception {
	    this.qname = rs.getString("qname");
        this.text = rs.getString("text");
        this.difficulty = rs.getInt("difficulty");
        this.hint = rs.getString("hint");
        this.correctPoints = rs.getInt("correct_points");
        this.penaltyPoints = rs.getInt("penalty_points");
        this.explanation = rs.getString("explanation");
    }

    public String getQname() {
		return qname;
	}
	public String getText() {
		return text;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public String getHint() {
		return hint;
	}
	public int getCorrectPoints() {
		return correctPoints;
	}
	public int getPenaltyPoints() {
		return penaltyPoints;
	}
	public String getExplanation() {
		return explanation;
	}
	
	
}
