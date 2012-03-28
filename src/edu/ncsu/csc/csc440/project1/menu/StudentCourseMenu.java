package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentCourseMenu extends Menu {

    private String cid;
    private String sid;
    
    public StudentCourseMenu(String sid, String cid) {
        this.cid = cid;
        this.sid = sid;
    }

    public MenuChoice[] getChoices() {
        MenuChoice[] choices = {
            new MenuChoice("S", "View Scores"),
            new MenuChoice("H", "Attempt Homework"),
            new MenuChoice("P", "View Past Submissions"),
            new MenuChoice("X", "Back")
        };
		return choices;
	}
    
    public boolean onChoice(MenuChoice choice) throws Exception {
		if (choice.shortcut.equals("X")){
			return false;
		} else if (choice.shortcut.equals("S")) {
		    StudentViewScoresMenu menu = new StudentViewScoresMenu(sid, cid);
			menu.menuLoop();
		    return true;
		} else if (choice.shortcut.equals("H")) {
			StudentAttemptHomeworkSelectMenu menu = new StudentAttemptHomeworkSelectMenu(sid, cid);
			menu.menuLoop();
			return true;
		} else if (choice.shortcut.equals("P")) {
			StudentViewPastSubmissionsMenu menu = new StudentViewPastSubmissionsMenu(sid, cid);
			menu.menuLoop();
			return true;
		} else {
			throw new RuntimeException("Should not get here.");
		}
	}
    
}
