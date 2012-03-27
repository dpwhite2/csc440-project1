package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * 
 */

/**
 * @author Allison
 *
 */
public class StudentMenu extends Menu{

	private String sid;
	
	public StudentMenu(String id){
		this.sid = id;
	}
	
	public MenuChoice[] getChoices() {
        MenuChoice[] menuChoices = {
            new MenuChoice("S", "Select Course"),
            new MenuChoice("A", "Add Course"),
            new MenuChoice("X", "Back")
		};
		return menuChoices;
	}

	/* (non-Javadoc)
	 * @see Menu#onChoice(MenuChoice)
	 */
	public boolean onChoice(MenuChoice choice) throws Exception {

		if(choice.shortcut.equals("S")){
			StudentSelectCourseMenu menu = new StudentSelectCourseMenu(sid);
			menu.menuLoop();
		}
		else if(choice.shortcut.equals("A")){
            addCourse();
		}
		else if(choice.shortcut.equals("X")){
			return false;
		}
		else{
			throw new RuntimeException("StudentMenu.onChoice() couldn't match choices");
		}
		return false;
	}
    
    private void addCourse() {
        // prompt for course token
        // if token not found, report invalid token
        // if course is after registration date, print error
        // else, add student to course.
    }

}
