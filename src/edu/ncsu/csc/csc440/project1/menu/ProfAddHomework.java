/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.util.Scanner;

/**
 * @author Allison
 *
 */
public class ProfAddHomework{
	
	private int pid;
	private int cid;
	private String prompt = "Please enter data for new homework with the following fields:\n" +
			"<name of homework> <start date> <end date> <number of attempts allowed>\n" +
			"<score selection scheme> <points for correct answer> <points deducted for incorrect answer>";
	
	public ProfAddHomework(int profID, int courseID){
		this.pid = profID;
		this.cid = courseID;
	}
	
	public boolean run(){
		//TODO finish
		String answer = promptUser(prompt);
		
		try{
			Scanner scan = new Scanner(answer);
			String cid = scan.next();
			String cname = scan.next();
			String start = scan.next();
			String end = scan.next();
		}
		catch(Exception e){
			
		}
		
		return false;
	}
	
	public String promptUser(String prompt) {
        System.out.print(prompt);
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
	
	
}