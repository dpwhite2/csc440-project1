package edu.ncsu.csc.csc440.project1.objs;

import java.sql.ResultSet;
import java.util.Date;

public class Exercise {
	private int eid;
    private String cid;
	private Date startDate;
	private Date endDate;
	private int correctPoints;
	private int penaltyPoints;
	private int seed;
	private String scoreMethod;
	private int maximumAttempts;
	
	public Exercise() {
		this.eid = -1;
        this.cid = "";
		this.startDate = new Date();
		this.endDate = new Date();
		this.correctPoints = -1;
		this.penaltyPoints = -1;
		this.seed = -1;
		this.scoreMethod = "";
        this.maximumAttempts = 1;
	}
	
	public Exercise(int eid, String cid, Date startDate, Date endDate, int correctPoints,
			int penaltyPoints, int seed, String scoreMethod, int maximumAttempts) {
		super();
		this.eid = eid;
        this.cid = cid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.correctPoints = correctPoints;
		this.penaltyPoints = penaltyPoints;
		this.seed = seed;
		this.scoreMethod = scoreMethod;
        this.maximumAttempts = maximumAttempts;
	}
	
	public Exercise(ResultSet rs) throws Exception {
	    this.eid = rs.getInt("eid");
        this.cid = rs.getString("cid");
        this.startDate = rs.getDate("startdate");
        this.endDate = rs.getDate("enddate");
        this.correctPoints = rs.getInt("correct_points");
        this.penaltyPoints = rs.getInt("penalty_points");
        this.seed = rs.getInt("seed");
        this.scoreMethod = rs.getString("score_method");
        this.maximumAttempts = rs.getInt("maximum_attempts");
	}

	public int getEid() {
		return eid;
	}
	public String getCid() {
        return cid;
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
