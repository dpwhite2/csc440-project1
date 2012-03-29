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
public class ProfSelectQuestionTopicMenu extends Menu{
	
	private int pid;
	private String cToken;
	private MenuChoice[] menuChoices;
	
	public ProfSelectQuestionTopicMenu(int profID, String courseToken){
		
		this.pid = profID;
		this.cToken = courseToken;
	}

	public MenuChoice[] getChoices() throws Exception {
		try{
			Connection conn = DBConnection.getConnection();
			//get number of topics for course
			PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM TopicPerCourse, Course C WHERE (C.token = ?) AND " +
					"(TopicPerCourse.cid = C.cid)");
			stmt1.setString(1, this.cToken);
	        ResultSet rs = stmt1.executeQuery();
	        if(rs.next()) menuChoices = new MenuChoice[rs.getInt("COUNT(*)")+1];
	        System.out.println("There are "+(menuChoices.length-1)+" topics for this course.");
	        
	        //Get list of topics
			PreparedStatement stmt2 = conn.prepareStatement("SELECT T.tid, T.text FROM Topic T, Course C, TopicPerCourse P WHERE (C.token=?) AND " +
					"(P.cid = C.cid) AND (P.tid = T.tid)");
	        stmt2.setString(1, this.cToken);
	        rs = stmt2.executeQuery();
	        int i = 0;
	        while (rs.next()) {
	        	int tID = rs.getInt("tid");
	            String tName = rs.getString("text");
	            menuChoices[i]= new MenuChoice(String.valueOf(tID), tName);
	            i++;
	        }
	        menuChoices[i]= new MenuChoice("X", "Back");
			}
			catch(Exception e){
				System.out.println("Problem in creating choice list forProfSelectQuestionTopicMenu: "+ e.getMessage());
			}
			
			return menuChoices;
	}

	
	public boolean onChoice(MenuChoice choice) throws Exception {
		if(choice.shortcut.equals("X")) return false;
		else{
			for(int i =0; i< menuChoices.length;i++){
				if(choice.shortcut.equals(menuChoices[i].shortcut)){
					//ProfCreateQuestion menu = new ProfCreateQuestion(Integer.parseInt(choice.shortcut));
					//while(!menu.run());
					//this.menuLoop(); //on next menu "back", run this menu again
					break;
				}
			}
		}
	
		return false;
	}

}
