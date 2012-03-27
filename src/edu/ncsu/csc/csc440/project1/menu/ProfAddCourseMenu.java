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
	
	private String promptText = "Please enter new course information in this order: <id> <coursename> <startdate> <enddate> <your_prof_id> \n All dates should be in the format mm/dd/yyyy\n";
	
	public boolean run(){
			String answer = promptUser(promptText);
			
			try{
				Scanner scan = new Scanner(answer);
				String cid = scan.next();
				String cname = scan.next();
				String start = scan.next();
				String end = scan.next();
				String profid = scan.next();

				//convert start & end into oracle TIMESTAMP format
				scan = new Scanner(start).useDelimiter("/");
				String mm = scan.next();
				String dd = scan.next();
				String yyyy = scan.next();

				String startTimestamp = yyyy+"-"+mm+"-"+dd+" 00:00:00.0";

				scan = new Scanner(end).useDelimiter("/");
				mm = scan.next();
				dd = scan.next();
				yyyy = scan.next();

				String endTimestamp = yyyy+"-"+mm+"-"+dd+" 00:00:00.0";

				String query = "INSERT INTO Course VALUES ('"+cid+"', '"+cname+"', timestamp'"+startTimestamp+"', timestamp'"+endTimestamp+"', "+profid+")";
				System.out. println(query);
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
