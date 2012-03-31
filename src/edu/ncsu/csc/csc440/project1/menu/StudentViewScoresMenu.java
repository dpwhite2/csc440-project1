package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.db.ExerciseDAO;
import edu.ncsu.csc.csc440.project1.objs.Exercise;
import edu.ncsu.csc.csc440.project1.objs.ExerciseInfo;

public class StudentViewScoresMenu extends Menu {
    
    class StudentViewScoresMenuChoice extends MenuChoice {
        public int eid = -1;
        public StudentViewScoresMenuChoice(String shortcut, String description, int eid) {
            super(shortcut, description);
            this.eid = eid;
        }
    }
	
	private String sid;
	private String cid;

	public StudentViewScoresMenu(String sid, String cid) {
		this.sid = sid;
		this.cid = cid;
	}
	
	public String headerMsg() {
        return "Submitted Homework Scores";
    }
	
	private ArrayList<Exercise> getExercises() throws Exception {
	    return ExerciseDAO.getExercisesForCourse(cid);
	}

	@Override
	public MenuChoice[] getChoices() throws Exception {
		// Show all completed homeworks.
	    
	    // get exercises
	    ArrayList<Exercise> exercises = getExercises();
	    // for each exercise, show: name, score, submission count, submissions remaining
	    ArrayList<ExerciseInfo> infos = new ArrayList<ExerciseInfo>();
	    for (Exercise ex: exercises) {
	        infos.add(ExerciseInfo.getExerciseInfo(ex.getEid(), sid));
	    }
	    MenuChoice[] choices = new MenuChoice[infos.size() + 1];
	    for (int i=0; i<infos.size(); i++) {
	        ExerciseInfo info = infos.get(i);
	        String shortcut = String.valueOf(i+1);
	        String label = String.format("%s -- %d/%d -- submissions: %d/%d", 
	                                     info.getName(), info.getScore(), 
	                                     info.getPossibleScore(), 
	                                     info.getSubmissionCount(),
	                                     info.getSubmissionMax());
	        choices[i] = new StudentViewScoresMenuChoice(shortcut, label, info.getEid());
	    }
	    choices[infos.size()] = new MenuChoice("X", "Back");
		return choices;
	}

	@Override
	public boolean onChoice(MenuChoice choice) throws Exception {
		if (choice.shortcut.equals("X")) {
			return false;
		} else {
		    // just return no matter what the choice is
		    return false;
		}
	}

}
