package edu.ncsu.csc.csc440.project1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    
}
