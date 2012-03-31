package edu.ncsu.csc.csc440.project1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import edu.ncsu.csc.csc440.project1.objs.AttemptAnswer;

public class AttemptAnswerDAO {
    
    public static ArrayList<AttemptAnswer> getAttemptAnswersAndText(int attid, int qposition) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT AA.*, A.text, A.correct FROM AttemptAnswer AA, Answer A WHERE AA.attid=? AND AA.qposition=? AND A.ansid=AA.ansid");
            stmt.setInt(1, attid);
            stmt.setInt(2, qposition);
            ResultSet rs = stmt.executeQuery();
            ArrayList<AttemptAnswer> answers = new ArrayList<AttemptAnswer>();
            while (rs.next()) {
                AttemptAnswer ans = new AttemptAnswer(rs);
                ans.setText(rs.getString("text"));
                ans.setCorrect(rs.getBoolean("correct"));
                answers.add(ans);
            }
            System.out.printf("DBG: number of answers found: %d\n",answers.size());
            return answers;
        } finally {
            conn.close();
        }
    }
    
}
