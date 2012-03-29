/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * @author Allison
 *
 */
public class ProfEditHomeworkMenu {

	private int eid;
	
	public ProfEditHomeworkMenu(int hwID){
		this.eid = hwID;
	}
	
	public boolean run(){
		//view current HW
		
		try{
			//retrieve HW
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM Exercise WHERE eid= ?");
			stmt1.setInt(1, this.eid);
	        ResultSet rs = stmt1.executeQuery();
	        /*
	         * eid                 INTEGER                     NOT NULL,
    		   cid                 VARCHAR2(32)                NOT NULL,
    		   ename               VARCHAR2(1000)  DEFAULT '',
    		   startdate           DATE                        NOT NULL,
    	       enddate             DATE                        NOT NULL,
    		   correct_points      INTEGER                     NOT NULL,
    		   penalty_points      INTEGER                     NOT NULL,
    		   seed                INTEGER         DEFAULT 0   NOT NULL,
    		   score_method        VARCHAR2(30)                NOT NULL,
    		   maximum_attempts    INTEGER         DEFAULT 1   NOT NULL,
	         */
	        
	        String ename = ""; 
	        Date startDate = new Date(2000,1,1); 
	        Date endDate = new Date(2000,1,1); 
	        String scoreMethod = ""; 
	        int correctPoints = 0; int penaltyPoints = 0; int maxAttempts = 0;
	        int success = 0;
	        
	        while (rs.next()) {
	        	rs.getInt("eid");
	            ename = rs.getString("ename");
	            startDate = rs.getDate("startdate");
	            endDate = rs.getDate("enddate");
	            correctPoints = rs.getInt("correct_points");
	            penaltyPoints = rs.getInt("penalty_points");
	            rs.getInt("seed");
	            scoreMethod = rs.getString("score_method");
	            maxAttempts = rs.getInt("maximum_attempts");
	            
	        }
	        System.out.println("Current HW --- "+ename);
	        System.out.println("Name: " + ename +
	        		"\nStart Date: " + startDate.toString() +
	        		"\nEnd Date: " + endDate.toString() +
	        		"\nPoints for correct answer: " + correctPoints +
	        		"\nPoints deducted for incorrect answer: " + penaltyPoints +
	        		"\nScore Method: " + scoreMethod +
	        		"\nMaximum attempts: " + maxAttempts);
	        
	        String prompt = "Please enter all data for edited homework with the following fields:\n" +
	    			"<name of homework> <start date> <end date> <number of attempts allowed>\n" +
	    			"<score selection scheme> <points for correct answer> <points deducted for incorrect answer>";
	        
	        try{
	        	String ans = promptUser(prompt);
				Scanner scan = new Scanner(ans);
				ename = scan.next();
	            startDate = convertToDate(scan.next());
	            endDate = convertToDate(scan.next());
	            maxAttempts = scan.nextInt();
	            scoreMethod = scan.next();
	            correctPoints = scan.nextInt();
	            penaltyPoints = scan.nextInt();
	            
	        
	            try{
					conn = DBConnection.getConnection();
					PreparedStatement stmt = conn.prepareStatement("UPDATE Exercise SET ename = ?, startdate = ?, enddate = ?, correct_points = ?, " +
	            			"penalty_points = ?, score_method = ?, maximum_attempts = ? WHERE eid = ?");
					stmt.setString(1, ename);
					stmt.setDate(2, startDate);
					stmt.setDate(3, endDate);
					stmt.setInt(4, correctPoints);
					stmt.setInt(5, penaltyPoints);
					stmt.setString(6, scoreMethod);
					stmt.setInt(7, maxAttempts);
					stmt.setInt(8, this.eid);
					
					//edit homework
					success = stmt.executeUpdate();

				}
				catch(Exception e){
					System.out.println("Problem editing HW: "+ e.getMessage());
				}
				
				//if added successfully, print line and return true
				if(success == 1){
					System.out.println("Exercise updated successfully.");
					return true;
				}
				//if not added successfully, return false
				else{
					System.out.println("Sorry, we could not update this exercise, please try again.");
					return false;
				}
	            
	        
	        
			}
			catch(Exception e){
				System.out.println("Error reading input for edited homwork: "+ e.getMessage());
			}
		}
		catch(Exception e){
			System.out.println(""+ e.getMessage());
		}
		
		return false;
	}

	public String promptUser(String prompt) {
		System.out.print(prompt);
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}

	private Date convertToDate (String date){
		Date d = new Date(2000,1,1);
		try{
		Scanner scan = new Scanner(date).useDelimiter("/");
		int mm = scan.nextInt();
		int dd = scan.nextInt();
		int yyyy = scan.nextInt();
		d = new Date(yyyy,mm,dd);
		}
		catch(Exception e){
			System.out.println("Error converting string to date: " + e.getMessage());
		}
		return d;
		
	}
}
