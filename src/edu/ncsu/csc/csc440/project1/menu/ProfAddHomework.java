/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * @author Allison
 *
 */
public class ProfAddHomework{
	
	private int pid;
	private String cToken;
	private String prompt = "Please enter data for new homework with the following fields:\n" +
			"<name of homework> <start date> <end date> <number of attempts allowed>\n" +
			"<score selection scheme> <points for correct answer> <points deducted for incorrect answer>";
	
	/*public enum ScoreScheme{
	    LATEST_ATTEMPT, MAXIMUM_SCORE, AVERAGE_SCORE 
	}*/
	
	public ProfAddHomework(int profID, String courseToken){
		this.pid = profID;
		this.cToken = courseToken;
	}
	
	public boolean run() throws Exception{
		
		String answer = promptUser(prompt);
		
		try{
			Scanner scan = new Scanner(answer);
			String ename = scan.next();
			String start = scan.next();
			String end = scan.next();
			int allowedAttempts = scan.nextInt();
			String scoreScheme = scan.next();
			int correctPts = scan.nextInt();
			int incorrectPts = scan.nextInt();
			
			//make seed
			Date ran = new Date();
			String timeString = Long.toString(ran.getTime());
			String lastChar = timeString.substring(timeString.length()-1);
			int seed = Integer.parseInt(lastChar);
			System.out.println("----The seed is: " +seed);
			
			//convert start & end into oracle TIMESTAMP format
			String startTimestamp = convertToTimestamp(start);
			String endTimestamp = convertToTimestamp(end);
			
			//match scoring scheme
			//ScoreScheme scheme = matchScheme(scoreScheme);
			
			
			String cid = findCID(this.cToken);
			// TODO write correct INSERT
			String query = "INSERT INTO Exercise VALUES (exercise_ids.nextval, '"+cid+"', '"+ename+"', timestamp'" +
					""+startTimestamp+"', timestamp'"+endTimestamp+"', "+correctPts+", "+incorrectPts+", "+seed+", '"+scoreScheme+"', "+allowedAttempts+", 0)";
			System.out. println(query);
			int success = 0;
			try{

				Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				//add homework
				success = stmt.executeUpdate(query);

			}
			catch(Exception e){
				System.out.println("Problem adding exercise: "+ e.getMessage());
			}
			
			//if added successfully, print line and return true
			if(success == 1){
				System.out.println("Exercise added successfully.");
				return true;
			}
			//if not added successfully, return false
			else{
				System.out.println("Sorry, we could not add this exercise, please try again.");
				return false;
			}
		}
		catch(RuntimeException e){
			throw new RuntimeException("Probem reading user input for new course: " + e.getMessage());
		}
	}
	/*
	public ScoreScheme matchScheme(String s){
		s = s.toLowerCase();
		if(s.equals("latest attempt")){
			return ScoreScheme.LATEST_ATTEMPT;
		}
		else if(s.equals("maximum score")){
			return ScoreScheme.MAXIMUM_SCORE;
		}
		else if(s.equals("average score")){
			return ScoreScheme.AVERAGE_SCORE;
		}
		else{
			System.out.println("Problem in matching scoring scheme.");
		}
		return ScoreScheme.LATEST_ATTEMPT;
	}
	*/
	
	public String promptUser(String prompt) {
        System.out.print(prompt);
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
	
	private String convertToTimestamp(String date){
		Scanner scan = new Scanner(date).useDelimiter("/");
		String mm = scan.next();
		String dd = scan.next();
		String yyyy = scan.next();

		String timestamp = yyyy+"-"+mm+"-"+dd+" 00:00:00.0";
		return timestamp;
	}
	
	private String findCID(String token) throws Exception{
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT cid FROM Course WHERE token=?");
		stmt.setString(1, token);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String cid = rs.getString("cid");
        System.out.println("-----The CID for the course is:" + cid);
        return cid;
        
	}
	
	
}