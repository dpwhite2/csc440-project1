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
	
	private int pid;
	private String promptText; //might not need this?
	private MenuChoice[] menuChoices;
	
	public ProfSelectCourseMenu(int id){
		this.pid = id;
	}
	
	/* (non-Javadoc)
	 * @see Menu#getChoices()
	 */
	public MenuChoice[] getChoices() {
	
		try{
		//find all courses this Professor teaches
		Connection conn = DBConnection.getConnection();
		//get number of course he/she teaches
		System.out.println("---PID is: "+ this.pid);
		PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Course WHERE pid=?");
		stmt1.setInt(1, this.pid);
        ResultSet rs = stmt1.executeQuery();
        if(rs.next()) menuChoices = new MenuChoice[rs.getInt("COUNT(*)")+1];
        System.out.println("You teach "+(menuChoices.length-1)+" courses.");
        
        //Get list of course names
		PreparedStatement stmt2 = conn.prepareStatement("SELECT token, cname FROM Course WHERE pid=?");
        stmt2.setInt(1, this.pid);
        rs = stmt2.executeQuery();
        int i = 0;
        while (rs.next()) {
        	String courseID = rs.getString("token");
            String courseName = rs.getString("cname");
            menuChoices[i]= new MenuChoice(courseID, courseName);
            i++;
        }
        menuChoices[i]= new MenuChoice("X", "Back");
		}
		catch(Exception e){
			System.out.println("Problem in creating choices for ProfSelectCourseMenu: "+ e.getMessage());
		}
		
		return menuChoices;
	}

	/* (non-Javadoc)
	 * @see Menu#onChoice(MenuChoice)
	 */
	public boolean onChoice(MenuChoice choice) throws Exception {
		if(choice.shortcut.equals("X")) return false;
		else{
			for(int i =0; i< menuChoices.length;i++){
				if(choice.shortcut.equals(menuChoices[i].shortcut)){
					ProfCourseMenu menu = new ProfCourseMenu(this.pid,choice.shortcut);
					menu.menuLoop();
					break;
				}
			}
		}
		return false;
	}
}
