package edu.ncsu.csc.csc440.project1.objs;

import java.sql.ResultSet;
import java.util.Date;

public class Attempt {
	
	private int attid;
	private int eid;
	private String sid;
	private int attnum;
	private Date submitTime;
	private int points;
	// The following comes from the Exercise
	private String ename = "";
	
	public Attempt() {
		this.attid = -1;
		this.eid = -1;
		this.sid = "";
		this.attnum = -1;
		this.submitTime = new Date();
        this.points = 0;
	}
	
	public Attempt(int attid, int eid, String sid, int attnum, Date submitTime, int points) {
		super();
		this.attid = attid;
		this.eid = eid;
		this.sid = sid;
		this.attnum = attnum;
		this.submitTime = submitTime;
        this.points = points;
	}

	public Attempt(ResultSet rs) throws Exception {
	    this.attid = rs.getInt("attid");
        this.eid = rs.getInt("eid");
        this.sid = rs.getString("sid");
        this.attnum = rs.getInt("attnum");
        this.submitTime = rs.getDate("submittime");
        this.points = rs.getInt("points");
    }

    public int getAttid() {
		return attid;
	}
	public int getEid() {
		return eid;
	}
	public String getSid() {
		return sid;
	}
	public int getAttnum() {
		return attnum;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
    public int getPoints() {
        return points;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
    public String getEname() {
        return ename;
    }
	
}
