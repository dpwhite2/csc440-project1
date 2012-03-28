package edu.ncsu.csc.csc440.project1.objs;

import java.util.Date;

public class Attempt {
	
	private int attid;
	private int eid;
	private String sid;
	private int attnum;
	private Date submitTime;
	
	public Attempt() {
		this.attid = -1;
		this.eid = -1;
		this.sid = "";
		this.attnum = -1;
		this.submitTime = new Date();
	}
	
	public Attempt(int attid, int eid, String sid, int attnum, Date submitTime) {
		super();
		this.attid = attid;
		this.eid = eid;
		this.sid = sid;
		this.attnum = attnum;
		this.submitTime = submitTime;
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
	
	
	
}
