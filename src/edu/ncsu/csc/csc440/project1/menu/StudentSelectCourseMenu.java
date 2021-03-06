package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.Course;

public class StudentSelectCourseMenu extends Menu {
    
    class CourseMenuChoice extends MenuChoice {
        public String cid = "";
        public CourseMenuChoice(String shortcut, String description, String cid) {
            super(shortcut, description);
            this.cid = cid;
        }
    }
    
    private String sid;
	
	public StudentSelectCourseMenu(String id){
		this.sid = id;
	}
	
	public String headerMsg() {
        return "Select a Course";
    }
    
    /**
     * All courses a Student is enrolled in
     */
    Course[] getCourses() throws Exception {
        // check username/password
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT COUNT(*) "
                + "FROM Course C, Enrolled E "
                + "WHERE E.cid=C.cid AND E.sid=?");
            stmt.setString(1, sid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                // something weird happened... we should always get a value, even 0
                return new Course[0];
            }
            int count = rs.getInt(1);
            Course[] courses = new Course[count];
            
            stmt = conn.prepareStatement(
                "SELECT C.cid, C.cname, C.token, C.startdate, C.enddate, C.pid "
                + "FROM Course C, Enrolled E "
                + "WHERE E.cid=C.cid AND E.sid=?");
            stmt.setString(1, sid);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return new Course[0];
            }
            for (int i=0; i<count; i++) {
                String cid = rs.getString("cid");
                String token = rs.getString("token");
                String cname = rs.getString("cname");
                Date startDate = rs.getDate("startdate");
                Date endDate = rs.getDate("enddate");
                int pid = rs.getInt("pid");
                courses[i] = new Course(cid, token, cname, startDate, endDate, pid);
                rs.next();
            }
            return courses;
        } finally {
            conn.close();
        }
    }
	
	public MenuChoice[] getChoices() throws Exception {
        // get all courses this student is enrolled in
        // also include a "back" option
        
        Course[] courses = getCourses();
        MenuChoice[] menuChoices = new MenuChoice[courses.length + 1];
        
        for (int i=0; i<courses.length; i++) {
            Course c = courses[i];
            menuChoices[i] = new CourseMenuChoice(String.valueOf(i+1), c.getCname(), c.getCid());
        }
        menuChoices[courses.length] = new MenuChoice("X", "Back");
		return menuChoices;
	}
    
    public boolean onChoice(MenuChoice choice_) throws Exception {
		if (choice_.shortcut.equals("X")){
			return false;
		} else {
            // assume shortcut is for a course in menu
            CourseMenuChoice choice = (CourseMenuChoice)choice_;
            String cid = choice.cid;
            StudentCourseMenu menu = new StudentCourseMenu(sid, cid);
            menu.menuLoop();
            return true;
        }
	}
    
}
