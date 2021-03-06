package edu.ncsu.csc.csc440.project1.objs;

import java.sql.ResultSet;
import java.util.Date;

public class Course {
    
    private String cid;
    private String token;
    private String cname;
    private Date startDate;
    private Date endDate;
    private int pid;
    
    public Course() {
        cid = "";
        token = "";
        cname = "";
        startDate = new Date();
        endDate = new Date();
        pid = -1;
    }
    
    public Course(String cid, String token, String cname, Date startDate, Date endDate, int pid) {
        this.cid = cid;
        this.token = token;
        this.cname = cname;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pid = pid;
    }
    
    public Course(ResultSet rs) throws Exception {
        this.cid = rs.getString("cid");
        this.token = rs.getString("token");
        this.cname = rs.getString("cname");
        this.startDate = rs.getDate("startdate");
        this.endDate = rs.getDate("enddate");
        this.pid = rs.getInt("pid");
    }
    
    public String getCid() { return cid; }
    public String getToken() { return token; }
    public String getCname() { return cname; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public int getPid() { return pid; }
}
