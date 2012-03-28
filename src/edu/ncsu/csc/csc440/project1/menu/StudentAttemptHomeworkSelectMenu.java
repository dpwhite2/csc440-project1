package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.Exercise;

public class StudentAttemptHomeworkSelectMenu extends Menu {
    
    class ExerciseMenuChoice extends MenuChoice {
        public int eid = -1;
        public ExerciseMenuChoice(String shortcut, String description, int eid) {
            super(shortcut, description);
            this.eid = eid;
        }
    }
	
	private String sid;
	private String cid;

	public StudentAttemptHomeworkSelectMenu(String sid, String cid) {
		this.sid = sid;
		this.cid = cid;
	}
	
	private ArrayList<Exercise> getAttemptReadyHomeworks() throws Exception {
	    String query = "SELECT "
	            + "FROM Exercise E "
	            + "WHERE E.startdate < ? AND ? < E.enddate AND E.cid=? "
	            + "AND (( "
	                 // exercises with at least one attempt remaining
	                 + "E.maximum_attempts > count( "
	                     + "SELECT * "
	                     + "FROM Attempt A "
	                     + "WHERE E.eid=A.eid AND A.sid=? "
	                 + ") "
	            + ") OR ( "
	                 // exercises with an attempt that hasn't been completed
	                 + "0 < count( "
	                     + "SELECT * "
	                     + "FROM Attempt A "
	                     + "WHERE E.eid=A.eid AND A.sid=? AND A.submittime=NULL "
	                 + ") "
	            + ")) ";
	    Connection conn = null;
	    ArrayList<Exercise> list = new ArrayList<Exercise>();
	    try {
	        conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query);
	        Date now = new Date(System.currentTimeMillis());
	        stmt.setDate(1, now);
            stmt.setDate(2, now);
            stmt.setString(3, cid);
            stmt.setString(4, sid);
            stmt.setString(5, sid);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Exercise ex = new Exercise(rs);
	            list.add(ex);
	        }
	    } finally {
	        conn.close();
	    }
	    return list;
	}

	@Override
	public MenuChoice[] getChoices() throws Exception {
		// Show all open homeworks.
		// That is, all homeworks that meet the following conditions:
		//		startDate <= now <= endDate
		//		( (count(started attempts) < maximum attempts AND count(not completed) == 0)
		//			OR...
		//		 (not completed) )
	    ArrayList<Exercise> exercises = getAttemptReadyHomeworks();
	    MenuChoice[] choices = new MenuChoice[exercises.size() + 1];
	    
	    for (int i=0; i<exercises.size(); i++) {
	        Exercise ex = exercises.get(i);
	        String label = String.valueOf(ex.getEid());
	        int eid = ex.getEid();
	        choices[i] = new ExerciseMenuChoice(String.valueOf(i), label, eid);
	    }
	    
	    choices[exercises.size()] = new MenuChoice("X", "Back");
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
