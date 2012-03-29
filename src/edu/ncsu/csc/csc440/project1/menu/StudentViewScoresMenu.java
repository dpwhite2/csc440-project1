package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.Exercise;

public class StudentViewScoresMenu extends Menu {
    
    class StudentViewScoresMenuChoice extends MenuChoice {
        public int eid = -1;
        public StudentViewScoresMenuChoice(String shortcut, String description, int eid) {
            super(shortcut, description);
            this.eid = eid;
        }
    }
    
    class ExerciseInfo {
        public String name;
        public int score;
        public int maxScore;
        public int submissionCount;
    }
	
	private String sid;
	private String cid;

	public StudentViewScoresMenu(String sid, String cid) {
		this.sid = sid;
		this.cid = cid;
	}
	
	private ArrayList<Exercise> getExercises() throws Exception {
	    Connection conn = null;
	    try {
	        conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement("SELECT E.* FROM Exercises E WHERE E.cid=?");
	        stmt.setString(1, cid);
	        ResultSet rs = stmt.executeQuery();
	        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
	        while (rs.next()) {
	            exercises.add(new Exercise(rs));
	        }
	        return exercises;
	    } finally {
	        conn.close();
	    }
	}
	
//	private ExerciseInfo getExerciseInfo(Exercise ex) throws Exception {
//	    TODO:
//	    Connection conn = null;
//	    try {
//	        conn = DBConnection.getConnection();
//	        PreparedStatement stmt = conn.prepareStatement("SELECT A.* FROM Attempt A WHERE A.eid=? AND A.sid=?");
//	        stmt.setInt(1, ex.getEid());
//	        stmt.setString(2, sid);
//	        ResultSet rs = stmt.executeQuery();
//	    } finally {
//	        conn.close();
//	    }
//	}

	@Override
	public MenuChoice[] getChoices() throws Exception {
		// Show all completed homeworks.
	    
	    // get exercises
	    // for each exercise, show: name, score, submission count, submissions remaining
		MenuChoice[] choices = {
			new MenuChoice("X", "Back")
		};
		return choices;
	}

	@Override
	public boolean onChoice(MenuChoice choice) throws Exception {
		if (choice.shortcut.equals("X")) {
			return false;
		} else {
			throw new RuntimeException("Should not get here.");
		}
	}

}
