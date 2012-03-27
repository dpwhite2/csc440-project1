package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentCourseMenu extends Menu {

    private int cid;
    private String sid;
    
    public StudentCourseMenu(String sid, int cid) {
        this.cid = cid;
        this.sid = sid;
    }

    public MenuChoice[] getChoices() {
        MenuChoice[] choices = {
            new MenuChoice("X", "Back")
        };
		return choices;
	}
    
    public boolean onChoice(MenuChoice choice) throws Exception {
		if (choice.shortcut.equals("X")){
			return false;
		}
        return true;
	}
    
}
