package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.Attempt;

public class StudentViewPastSubmissionsMenu extends Menu {
    
    class PastAttemptsMenuChoice extends MenuChoice {
        public int attid = -1;
        public PastAttemptsMenuChoice(String shortcut, String description, int attid) {
            super(shortcut, description);
            this.attid = attid;
        }
    }
	
	private String sid;
	private String cid;

	public StudentViewPastSubmissionsMenu(String sid, String cid) {
		this.sid = sid;
		this.cid = cid;
	}
	
	private ArrayList<Attempt> getAttempts() throws Exception {
	    String s = "SELECT A.* FROM Attempt A, Exercise E "
	             + "WHERE A.submittime IS NOT NULL AND A.sid=? AND A.eid=E.eid AND E.cid=?";
	    Connection conn = null;
	    try {
	        conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(s);
	        stmt.setString(1, sid);
	        stmt.setString(2, cid);
	        ResultSet rs = stmt.executeQuery();
	        ArrayList<Attempt> attempts = new ArrayList<Attempt>();
	        while (rs.next()) {
	            attempts.add(new Attempt(rs));
	        }
	        return attempts;
	    } finally {
	        conn.close();
	    }
	}

	@Override
	public MenuChoice[] getChoices() throws Exception {
		// Show all submitted homeworks past due date
	    // Show all submitted homeworks within due date
	    ArrayList<Attempt> attempts = getAttempts();
	    MenuChoice[] choices = new MenuChoice[attempts.size() + 1];
	    for (int i=0; i<attempts.size(); i++) {
	        Attempt att = attempts.get(i);
            String label = "Attempt " + String.valueOf(i+1);
            int attid = att.getAttid();
	        choices[i] = new PastAttemptsMenuChoice(String.valueOf(i+1), label, attid);
	    }
		choices[attempts.size()] = new MenuChoice("X","Back");
		return choices;
	}

	@Override
	public boolean onChoice(MenuChoice choice_) throws Exception {
		if (choice_.shortcut.equals("X")) {
			return false;
		} else {
		    // assume choice is for a legitimate past submission choice
		    PastAttemptsMenuChoice choice = (PastAttemptsMenuChoice)choice_;
		    StudentViewSinglePastSubmissionMenu menu = new StudentViewSinglePastSubmissionMenu(sid, cid, choice.attid);
		    menu.menuLoop();
		    return true;
		}
	}

}
