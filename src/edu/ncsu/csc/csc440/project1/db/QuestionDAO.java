package edu.ncsu.csc.csc440.project1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.objs.Question;

public class QuestionDAO {
    
    public static Question getQuestionByAttempt(int attid, int qposition) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT Q.* FROM Question Q, AttemptQuestion AQ WHERE AQ.attid=? AND AQ.qposition=? AND AQ.qname=Q.qname");
            stmt.setInt(1, attid);
            stmt.setInt(2, qposition);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("ERROR: Cannot find given Question");
            }
            return new Question(rs);
        } finally {
            conn.close();
        }
    }
    
    public static ArrayList<Question> getQuestionsForEid(int eid) throws Exception {
        // get all questions for this exercise
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT Q.* FROM Question Q, ExerciseQuestion EQ WHERE EQ.eid=? AND Q.qname=EQ.qname");
            stmt.setInt(1, eid);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Question> questions = new ArrayList<Question>();
            while (rs.next()) {
                questions.add(new Question(rs));
            }
            return questions;
        } finally {
            conn.close();
        }
    }
    
}
