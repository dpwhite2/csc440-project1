package edu.ncsu.csc.csc440.project1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.objs.Exercise;

public class ExerciseDAO {
    
    public static ArrayList<Exercise> getExercisesForCourse(String cid) throws Exception {
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
    
    public static Exercise getExerciseForAttempt(int attid) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT E.* FROM Exercise E, Attempt AT WHERE E.eid=AT.eid AND AT.attid=?");
            stmt.setInt(1, attid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Cannot find Exercise.");
            }
            return new Exercise(rs);
        } finally {
            conn.close();
        }
    }
    
    public static Exercise getExerciseByEid(int eid) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT E.* FROM Exercise E WHERE E.eid=?");
            stmt.setInt(1, eid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Exercise not found.");
            }
            return new Exercise(rs);
        } finally {
            conn.close();
        }
    }
}
