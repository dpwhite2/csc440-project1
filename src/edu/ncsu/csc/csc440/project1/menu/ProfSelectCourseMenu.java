package edu.ncsu.csc.csc440.project1.menu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * 
 */

/**
 * @author Allison
 *
 */
public class ProfSelectCourseMenu extends Menu {
	
	int pid;
	private String promptText; //might not need this?
	private MenuChoice[] menuChoices;
	
	public ProfSelectCourseMenu(int id){
		pid = id;
	}
	
	/* (non-Javadoc)
	 * @see Menu#getChoices()
	 */
	public MenuChoice[] getChoices() {
	
		int count = menuChoices.length;
		try{
		//find all courses this Professor teaches
		Connection conn = DBConnection.getConnection();
		//get number of course he/she teaches
		PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Course WHERE pid=?");
		stmt1.setInt(1, this.pid);
        ResultSet rs = stmt1.executeQuery();
        menuChoices = new MenuChoice[rs.getInt("COUNT(*)")];
        System.out.println("You teach "+menuChoices.length+" courses.");
        
        //Get list of course names
		PreparedStatement stmt2 = conn.prepareStatement("SELECT cid, cname FROM Course WHERE pid=?");
        stmt2.setInt(1, this.pid);
        rs = stmt2.executeQuery();
        int i = 0;
        while (rs.next()) {
        	String courseID = rs.getString("cid");
            String courseName = rs.getString("cname");
            menuChoices[i]= new MenuChoice(courseID, courseName);
            i++;
        }
        
		}
		catch(Exception e){
			System.out.println("Problem in getting choices for ProfSelectCourseMenu: "+ e.getMessage());
		}
		
		return menuChoices;
	}

	/* (non-Javadoc)
	 * @see Menu#onChoice(MenuChoice)
	 */
	public boolean onChoice(MenuChoice choice) {
		// TODO finish
		
		for(int i =0; i< menuChoices.length;i++){
			if(choice.equals(menuChoices[i])){
				//make new ProfCourseMenu and pass it the cid from cid[i]
				break;
			}
		}
	
		return false;
	}
}
