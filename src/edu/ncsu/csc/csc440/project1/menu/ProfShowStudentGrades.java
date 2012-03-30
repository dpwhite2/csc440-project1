/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * @author Allison
 *
 */
public class ProfShowStudentGrades {
	
	private int pid;
	private String cToken;
	
	public ProfShowStudentGrades(int profID, String token){
		this.pid = profID;
		this.cToken = token;
	}
	
	public boolean run(){
		String[] students = new String[0];
		try{
			Connection conn = DBConnection.getConnection();
			//get number of students enrolled in this course
			PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Student S, Enrolled E, Course C WHERE (C.token = ?) AND " +
					"(E.sid = S.sid) AND (C.cid = E.cid)");
			stmt1.setString(1, this.cToken);
	        ResultSet rs = stmt1.executeQuery();
	        if(rs.next()){
	        	students = new String[rs.getInt("COUNT(*)")];

	        	System.out.println("There are "+(students.length)+" students enrolled in this course.");

	        	//Get list of student usernames
	        	PreparedStatement stmt2 = conn.prepareStatement("SELECT S.sid FROM Student S, Enrolled E, Course C WHERE (C.token = ?) AND " +
	        	"(E.sid = S.sid) AND (C.cid = E.cid)");
	        	stmt2.setString(1, this.cToken);
	        	rs = stmt2.executeQuery();
	        	int i = 0;
	        	while (rs.next()) {
	        		students[i] = rs.getString("sid");
	        		i++;
	        	}
	        }
	        else{
	        	System.out.println("Sorry, you have no students enrolled in your classes.");
	        	return false;
	        }
		}
		catch(Exception e){
			System.out.println("Problem in getting list of students for ProfShowStudentGrades: "+ e.getMessage());
		}
		
		// TODO - group attempts by assignment and show final grade for each assignment
		//for each student, print out Homework name, student score
		for(int x = 0; x<students.length;x++){
			System.out.println("---" + students[x] + " ---");
			try{
				Connection conn = DBConnection.getConnection();
				//Get list of homework attempts for each student
				PreparedStatement stmt = conn.prepareStatement("SELECT E.ename, A.points " +
						"FROM Attempt A JOIN Exercise E ON E.eid = A.eid JOIN Course C ON C.cid = E.cid " +
						"WHERE (A.sid = ?) AND (C.token =?)");
				stmt.setString(1, students[x]);
				stmt.setString(2, this.cToken);
				ResultSet rs = stmt.executeQuery();
				int i = 0;
				while (rs.next()) {
					System.out.println(rs.getString("ename") + " | " + rs.getString("points"));
				}
			}
			catch(Exception e){
				System.out.println("Problem in getting list of homework and scores for students for ProfShowStudentGrades: "+ e.getMessage());
			}
	
		}	
		return false;
	}
}
