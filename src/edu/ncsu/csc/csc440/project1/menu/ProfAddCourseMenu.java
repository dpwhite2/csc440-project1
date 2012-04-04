/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * @author Allison
 *
 */
public class ProfAddCourseMenu{
	
	private int pid;
	
	public ProfAddCourseMenu(int id){
		
		this.pid = id;
	}
	public boolean run(){
		try{	
			String cid = promptUser("Please enter the ID for the new course.\n");
			String cToken = promptUser("Please enter the course token.\n");
			String cname = promptUser("Please enter the name for the new course.\n");
			String start = promptUser("Please enter the start date for the new course. (in the form mm/dd/yyy)\n");
			String end = promptUser("Please enter the end date for the new course. (in the form mm/dd/yyy)\n");

			//convert start & end into oracle TIMESTAMP format
			Scanner scan = new Scanner(start).useDelimiter("/");
			String mm = scan.next();
			String dd = scan.next();
			String yyyy = scan.next();

			String startTimestamp = yyyy+"-"+mm+"-"+dd+" 00:00:00.0";

			scan = new Scanner(end).useDelimiter("/");
			mm = scan.next();
			dd = scan.next();
			yyyy = scan.next();

			String endTimestamp = yyyy+"-"+mm+"-"+dd+" 00:00:00.0";

			String query = "INSERT INTO Course VALUES ('"+cid+"', '"+cToken+"', '"+cname+"', timestamp'"+startTimestamp+"', timestamp'"+endTimestamp+"', "+this.pid+")";
			//System.out. println(query);
			int success = 0;

			try{
				//find all courses this Professor teaches
				Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				success = stmt.executeUpdate(query);

			}
			catch(Exception e){
				System.out.println("Problem adding course: "+ e.getMessage());
			}


			//if added successfully, print line and return true
			if(success == 1){
				System.out.println("Course added successfully.");
				return true;
			}
			//if not added successfully, return false
			else{
				System.out.println("Sorry, we could not add this course, please try again.");
				return false;
			}
		}
		catch(Exception e){
			throw new RuntimeException("Probem reading user input for new course");
		}
	}
	
	public String promptUser(String prompt) {
        System.out.print(prompt);
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
	
}
