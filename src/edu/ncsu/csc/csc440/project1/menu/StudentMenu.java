package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Scanner;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.Course;

/**
 * 
 */

/**
 * @author Allison
 *
 */
public class StudentMenu extends Menu{

	private String sid;
	
	public StudentMenu(String id){
		this.sid = id;
	}
	
	public MenuChoice[] getChoices() {
        MenuChoice[] menuChoices = {
            new MenuChoice("S", "Select Course"),
            new MenuChoice("A", "Add Course"),
            new MenuChoice("X", "Back")
		};
		return menuChoices;
	}

	/* (non-Javadoc)
	 * @see Menu#onChoice(MenuChoice)
	 */
	public boolean onChoice(MenuChoice choice) throws Exception {

		if(choice.shortcut.equals("S")){
			StudentSelectCourseMenu menu = new StudentSelectCourseMenu(sid);
			menu.menuLoop();
			return true;
		}
		else if(choice.shortcut.equals("A")){
            addCourse();
            return true;
		}
		else if(choice.shortcut.equals("X")){
			return false;
		}
		else{
			throw new RuntimeException("StudentMenu.onChoice() couldn't match choices");
		}
	}
	
	private Course getCourseWithToken(String token) throws Exception {
	    Connection conn = null;
	    try {
	        conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Course C WHERE C.cid=?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.printf("Course token not found.\n");
                return null;
            } else {
                return new Course(rs);
            }
	    } finally {
	        conn.close();
	    }
	}
	
	private boolean isStudentRegistered(String cid) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM Enrolled E WHERE E.cid=? AND E.sid=?");
            stmt.setString(1, cid);
            stmt.setString(2, sid);
            ResultSet rs = stmt.executeQuery();
            // rs.next() should always work here, since count() should always return one row
            rs.next();
            return rs.getInt(1) > 0;
        } finally {
            conn.close();
        }
    }
	
	private void registerWithCourse(String cid) throws Exception {
	    Connection conn = null;
	    try {
	        conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Enrolled VALUES (?, ?)");
	        stmt.setString(1, cid);
	        stmt.setString(2, sid);
	        stmt.executeUpdate();
	    } finally {
	        conn.close();
	    }
	}
    
    private void addCourse() throws Exception {
        // prompt for course token
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter course token: ");
        String token = scan.nextLine();
        // if token not found, report invalid token
        Course course = getCourseWithToken(token);
        if (course != null) {
            Date now = new Date();
            // if course is after registration date, print error
            if (!course.getStartDate().before(now) || !course.getEndDate().after(now)) {
                System.out.printf("Course is not open for registration.\n");
                return;
            } else if (isStudentRegistered(course.getCid()) == true) {
                System.out.printf("Student is already registered in this class.\n");
                return;
            } else {
                // else, add student to course.
                registerWithCourse(course.getCid());
            }
        }
        
        
    }

}
