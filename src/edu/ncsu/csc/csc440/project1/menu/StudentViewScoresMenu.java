package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
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
    
    /*class ExerciseInfo {
        public String name;
        public int score;
        public int possibleScore;
        public int submissionCount;
        public int submissionMax;
        public int eid;
        
        public ExerciseInfo() {
            name = "";
            score = -1;
            possibleScore = -1;
            submissionCount = -1;
            submissionMax = -1;
            eid = -1;
        }
        
        public ExerciseInfo(String name, int score, int possibleScore,
                int submissionCount, int submissionMax, int eid) {
            this.name = name;
            this.score = score;
            this.possibleScore = possibleScore;
            this.submissionCount = submissionCount;
            this.submissionMax = submissionMax;
            this.eid = eid;
        }
    }*/
	
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
	    Connection conn = null;
	    try {
	        conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement("SELECT E.* FROM Exercise E WHERE E.cid=?");
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
	
	/*private int scoreFirstAttempt(ArrayList<Integer> scores) {
	    return scores.get(0).intValue();
	}
    private int scoreLastAttempt(ArrayList<Integer> scores) {
        return scores.get(scores.size()-1).intValue();
    }
    private int scoreMaxAttempt(ArrayList<Integer> scores) {
        int max = 0;
        for (Integer x: scores) {
            max = Math.max(max, x.intValue());
        }
        return max;
    }
    private int scoreAvgAttempt(ArrayList<Integer> scores) {
        int sum = 0;
        for (Integer x: scores) {
            sum += x.intValue();
        }
        return (int)((float)sum / (float)scores.size());
    }
	
	private ExerciseInfo getExerciseInfo(Exercise ex) throws Exception {
	    Connection conn = null;
	    try {
	        conn = DBConnection.getConnection();
	        // Get Exercise info (common to all who take the exercise)
	        PreparedStatement stmt = conn.prepareStatement("SELECT E.* FROM Exercise E WHERE E.eid=?");
	        stmt.setInt(1, ex.getEid());
	        ResultSet rs = stmt.executeQuery();
	        if (!rs.next()) {
	            throw new RuntimeException("ERROR: No exercises found matching query.");
	        }
	        String method = rs.getString("score_method");
	        int correctPoints = rs.getInt("correct_points");
            int questionCount = rs.getInt("question_count");
            int possibleScore = correctPoints * questionCount;
            int maxAttempts = rs.getInt("maximum_attempts");
            String ename = rs.getString("ename");
            
            // Get Attempts info (specific to this Student)
            stmt = conn.prepareStatement("SELECT A.* FROM Attempt A WHERE A.eid=? AND A.sid=?");
            stmt.setInt(1, ex.getEid());
            stmt.setString(2, sid);
            rs = stmt.executeQuery();
            ArrayList<Integer> scores = new ArrayList<Integer>();
            if (rs.next()) {
                scores.add(rs.getInt("points"));
            }
            int submissionCount = scores.size();
            
            // Calculate score
            int score = -1;
            if (method.equals("first attempt")) {
                score = scoreFirstAttempt(scores);
            } else if (method.equals("last attempt")) {
                score = scoreLastAttempt(scores);
            } else if (method.equals("average")) {
                score = scoreAvgAttempt(scores);
            } else if (method.equals("max")) {
                score = scoreMaxAttempt(scores);
            } else {
                throw new RuntimeException("Unknown scoring method: " + method);
            }
	        
            // Build ExerciseInfo
            return new ExerciseInfo(ename, score, possibleScore, submissionCount, maxAttempts, ex.getEid());
	    } finally {
	        conn.close();
	    }
	}*/

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
