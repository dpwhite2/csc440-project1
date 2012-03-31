package edu.ncsu.csc.csc440.project1.objs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

public class ExerciseInfo {

    private String sid;
    private String name;
    private int score;
    private int possibleScore;
    private int submissionCount;
    private int submissionMax;
    private int eid;
    private String method;
    
    public ExerciseInfo() {
        sid = "";
        name = "";
        score = -1;
        possibleScore = -1;
        submissionCount = -1;
        submissionMax = -1;
        eid = -1;
        method = "";
    }
    
    public ExerciseInfo(String sid, int eid, String name, int score, int possibleScore,
            int submissionCount, int submissionMax, String method) {
        this.sid = sid;
        this.eid = eid;
        this.name = name;
        
        this.score = score;
        this.possibleScore = possibleScore;
        this.submissionCount = submissionCount;
        this.submissionMax = submissionMax;
        this.method = method;
    }
    
    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getPossibleScore() {
        return possibleScore;
    }

    public int getSubmissionCount() {
        return submissionCount;
    }

    public int getSubmissionMax() {
        return submissionMax;
    }

    public int getEid() {
        return eid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    /* Scoring methods */
    private static int scoreFirstAttempt(ArrayList<Attempt> attempts) {
        return attempts.get(0).getPoints();
    }
    private static int scoreLastAttempt(ArrayList<Attempt> attempts) {
        return attempts.get(attempts.size()-1).getPoints();
    }
    private static int scoreMaxAttempt(ArrayList<Attempt> attempts) {
        int max = 0;
        for (Attempt x: attempts) {
            max = Math.max(max, x.getPoints());
        }
        return max;
    }
    private static int scoreAvgAttempt(ArrayList<Attempt> attempts) {
        int sum = 0;
        for (Attempt x: attempts) {
            sum += x.getPoints();
        }
        return (int)((float)sum / (float)attempts.size());
    }
    
    private static Exercise getExercise(Connection conn, int eid) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("SELECT E.* FROM Exercise E WHERE E.eid=?");
        stmt.setInt(1, eid);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            throw new RuntimeException("ERROR: No exercises found matching query.");
        }
        return new Exercise(rs);
    }
    
    private static ArrayList<Attempt> getAttempts(Connection conn, int eid, String sid) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("SELECT A.* FROM Attempt A WHERE A.eid=? AND A.sid=?");
        stmt.setInt(1, eid);
        stmt.setString(2, sid);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Attempt> attempts = new ArrayList<Attempt>();
        if (rs.next()) {
            attempts.add(new Attempt(rs));
        }
        return attempts;
    }
    
    public static ExerciseInfo getExerciseInfo(int eid, String sid) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Exercise ex = getExercise(conn, eid);
            ArrayList<Attempt> attempts = getAttempts(conn, eid, sid);
            int submissionCount = attempts.size();
            
         // Calculate score
            int score = -1;
            if (ex.getScoreMethod().equals("first attempt")) {
                score = scoreFirstAttempt(attempts);
            } else if (ex.getScoreMethod().equals("last attempt")) {
                score = scoreLastAttempt(attempts);
            } else if (ex.getScoreMethod().equals("average")) {
                score = scoreAvgAttempt(attempts);
            } else if (ex.getScoreMethod().equals("max")) {
                score = scoreMaxAttempt(attempts);
            } else {
                throw new RuntimeException("Unknown scoring method: " + ex.getScoreMethod());
            }
            
            return new ExerciseInfo(sid, eid, ex.getEname(), score, ex.getPossibleScore(), 
                                    submissionCount, ex.getMaximumAttempts(), ex.getScoreMethod());
            
        } finally {
            conn.close();
        }
    }
}
