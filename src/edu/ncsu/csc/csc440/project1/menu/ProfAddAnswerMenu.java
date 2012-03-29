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
public class ProfAddAnswerMenu extends Menu{

	
	private int pid;
	private String cToken;
	private MenuChoice[] menuChoices;
	
	public ProfAddAnswerMenu(String token){
		this.cToken = token;
	}
	
	public MenuChoice[] getChoices() throws Exception {
		try{
			Connection conn = DBConnection.getConnection();
			//get number of questions for course
			PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM ExerciseQuestion X, Question Q, Course C, Exercise E WHERE (C.token = ?) AND " +
					"(C.cid = E.cid) AND (X.eid = E.eid) AND (X.qname = Q.qname)");
			stmt1.setString(1, this.cToken);
	        ResultSet rs = stmt1.executeQuery();
	        if(rs.next()) menuChoices = new MenuChoice[rs.getInt("COUNT(*)")+1];
	        System.out.println("There are "+(menuChoices.length-1)+" questions for this course.");
	        
	        //Get list of questions
			PreparedStatement stmt2 = conn.prepareStatement("SELECT Q.qname FROM ExerciseQuestion X, Question Q, Course C, Exercise E WHERE (C.token = ?) AND " +
			"(C.cid = E.cid) AND (X.eid = E.eid) AND (X.qname = Q.qname)");
	        stmt2.setString(1, this.cToken);
	        rs = stmt2.executeQuery();
	        int i = 0;
	        while (rs.next()) {
	        	String qName = rs.getString("qname");
	            menuChoices[i]= new MenuChoice(String.valueOf(i+1), qName);
	            i++;
	        }
	        menuChoices[i]= new MenuChoice("X", "Back");
			}
			catch(Exception e){
				System.out.println("Problem in creating choice list for ProfAddAnswerMenu: "+ e.getMessage());
			}
			
			return menuChoices;
	}


	public boolean onChoice(MenuChoice choice) throws Exception {
		if(choice.shortcut.equals("X")) return false;
		else{
			for(int i =0; i< menuChoices.length;i++){
				if(choice.shortcut.equals(menuChoices[i].shortcut)){
					//ProfAddAnswer addAnsMenu = new ProfAddAnswer(qName);
					//menu.run();
					break;
				}
			}
		}
		return false;
	}
	
	

}
