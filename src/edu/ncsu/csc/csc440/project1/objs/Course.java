package edu.ncsu.csc.csc440.project1.objs;

import java.util.Date;

public class Course {
    
    private String cid;
    private String cname;
    private Date startDate;
    private Date endDate;
    private int pid;
    
    public Course() {
        cid = -1;
        cname = "";
        startDate = new Date();
        endDate = new Date();
        pid = -1;
    }
    
    public Course(int cid, String cname, Date startDate, Date endDate, int pid) {
        this.cid = cid;
        this.cname = cname;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pid = pid;
    }
    
    public int getCid() { return cid; }
    public String getCname() { return cname; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public int getPid() { return pid; }
}
