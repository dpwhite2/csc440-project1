package edu.ncsu.csc.csc440.project1.objs;

import java.util.Date;

public class Exercise {
	private int eid;
	private Date startDate;
	private Date endDate;
	private int correctPoints;
	private int penaltyPoints;
	private int seed;
	private String scoreMethod;
	
	public Exercise() {
		this.eid = -1;
		this.startDate = new Date();
		this.endDate = new Date();
		this.correctPoints = -1;
		this.penaltyPoints = -1;
		this.seed = -1;
		this.scoreMethod = "";
	}
	
	public Exercise(int eid, Date startDate, Date endDate, int correctPoints,
			int penaltyPoints, int seed, String scoreMethod) {
		super();
		this.eid = eid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.correctPoints = correctPoints;
		this.penaltyPoints = penaltyPoints;
		this.seed = seed;
		this.scoreMethod = scoreMethod;
	}

	public int getEid() {
		return eid;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public int getCorrectPoints() {
		return correctPoints;
	}
	public int getPenaltyPoints() {
		return penaltyPoints;
	}
	public int getSeed() {
		return seed;
	}
	public String getScoreMethod() {
		return scoreMethod;
	}
}
