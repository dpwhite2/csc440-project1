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
public class ProfAddAnswer {
	
	private String qName;
	
	public ProfAddAnswer(String name){
		this.qName = name;
	}
	
	public boolean run(){
		
		/*
		qname           VARCHAR2(64)                NOT NULL,
    	ansid           INTEGER                     NOT NULL,
    	text            VARCHAR2(1000)              NOT NULL,
    	correct         INTEGER                     NOT NULL,
    	explanation     VARCHAR2(1000)  DEFAULT '',
    	hint            VARCHAR2(1000)  DEFAULT '',
		 */
		
		//get all input

		String text = promptUser("What is the text for this answer?");
		String correct = promptUser("Is this answer a correct answer? (y/n)");
		while(!checkYN(correct)){
			correct = promptUser("Sorry, your input is not valid. Please enter 'y' for 'correct or 'n' for incorrect" +
					"\nIs this answer a correct answer? (y/n)");
		}
		String explanation = promptUser("What is an explanation for this answer?");
		String hint = promptUser("What is a hint for this answer?");
	
		int correctInt = getInt(correct);
		int success = -1;
		
		//add input to ANSWER table
		try{
			String insertQuery = "INSERT INTO Answer VALUES ('"+this.qName+"', answer_ids.nextval, '" +
					""+text+"', "+correctInt+", '"+explanation+"', '" +hint+"')";
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
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
	 * Checks to see if the y/n input is okay
	 */
	public boolean checkYN(String ans){
		if(ans.equals("y") || ans.equals("yes") || ans.equals("n") || ans.equals("no")){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * Converts y/n to integer
	 */
	public int getInt(String ans){
		if(ans.equals("y") || ans.equals("yes")){
			return 1;
		}
		else if(ans.equals("n") || ans.equals("no")){
			return 0;
		}
		else{
			throw new RuntimeException("Problem converting input y/n into integer");
		}
	}
}
