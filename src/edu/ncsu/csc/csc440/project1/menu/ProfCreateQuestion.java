/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * @author Allison
 *
 */
public class ProfCreateQuestion {
	
	
	/**
	qname           VARCHAR2(64)                NOT NULL,
    text            VARCHAR2(1000)              NOT NULL,
    difficulty      INTEGER                     NOT NULL,
    hint            VARCHAR2(1000)  DEFAULT '',
    correct_points  INTEGER                     NOT NULL,
    penalty_points  INTEGER                     NOT NULL,
    explanation     VARCHAR2(1000)  DEFAULT '',
	 */
	
	public boolean run(){
		//get all input
		String qName = promptUser("What is the name of this question?");
		String qText = promptUser("What is the question?");
		String correctText = promptUser("What is the correct answer?");
		String[] incorrectText = new String[3];
		incorrectText[0] = promptUser("What is the 1st incorrect answer?");
		incorrectText[1] = promptUser("What is the 2nd incorrect answer?");
		incorrectText[2] = promptUser("What is the 3rd incorrect answer?");
		String hint = promptUser("What is a hint for this question?");
		String diff = promptUser("What is the difficulty of this question? (1 through 5)");
		while(!checkDiff(diff)){
			diff = promptUser("Sorry, your input is not valid. Please enter a number from 1 through 5." +
					"\nWhat is the difficulty of this question? (0 through 5)");
		}
		String correctPts = promptUser("How many points are given for a correct answer?");
		String incorrectPts = promptUser("How many points are deducted for an incorrect answer?");
		String explanation = promptUser("Please give an explanation for this question.");
		int success = -1;
		
		//add input to QUESTION table
		try{
			String insertQuery = "INSERT INTO Question VALUES ('"+qName+"', '"+qText+"', "+diff+", '"+hint+
			"', "+correctPts+", "+incorrectPts+",'"+explanation+"')";
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			success = stmt.executeUpdate(insertQuery);

		}
		catch(Exception e){
			System.out.println("Problem adding question: "+ e.getMessage());
		}
		
		/*
		qname           VARCHAR2(64)                NOT NULL,
    	ansid           INTEGER                     NOT NULL,
    	text            VARCHAR2(1000)              NOT NULL,
    	correct         INTEGER                     NOT NULL,
    	explanation     VARCHAR2(1000)  DEFAULT '',
    	hint 
		 */
		//add input to ANSWER table
		try{
			// TODO prompt for unique explanations for each answer
			//add correct answer
			String insertQuery = "INSERT INTO Answer VALUES ('"+qName+"', answer_ids.nextval, " +
					""+correctText+", 1, '"+explanation+"', '" +hint+"')";
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			success = stmt.executeUpdate(insertQuery);
			
			//add incorrect answer #1
			insertQuery = "INSERT INTO Answer VALUES ('"+qName+"', answer_ids.nextval, " +
					""+incorrectText[0]+", 1, '"+explanation+"', '" +hint+"')";
			stmt = conn.createStatement();
			success = stmt.executeUpdate(insertQuery);
			
			//add incorrect answer #2
			insertQuery = "INSERT INTO Answer VALUES ('"+qName+"', answer_ids.nextval, " +
					""+incorrectText[1]+", 1, '"+explanation+"', '" +hint+"')";
			stmt = conn.createStatement();
			success = stmt.executeUpdate(insertQuery);
			
			//add incorrect answer #3
			insertQuery = "INSERT INTO Answer VALUES ('"+qName+"', answer_ids.nextval, " +
					""+incorrectText[2]+", 1, '"+explanation+"', '" +hint+"')";
			stmt = conn.createStatement();
			success = stmt.executeUpdate(insertQuery);

		}
		catch(Exception e){
			System.out.println("Problem adding answer: "+ e.getMessage());
		}
		

		//if added successfully, print line and return true
		if(success == 1){
			System.out.println("Question added successfully.");
			return true;
		}
		//if not added successfully, return false
		else{
			System.out.println("Sorry, we could not add this question, please try again.");
			return false;
		}
		
	}
	
	public String promptUser(String prompt) {
        System.out.print(prompt);
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
	
	/*
	 * Checks to see if the difficulty input is okay
	 */
	public boolean checkDiff(String difficulty){
		int d = -1;
		try{
		d = Integer.parseInt(difficulty);
		}
		catch(Exception e){
			return false;
		}
		if(d <=5 || d >=1){
			return true;
		}
		else{
			return false;
		}
	}
}
