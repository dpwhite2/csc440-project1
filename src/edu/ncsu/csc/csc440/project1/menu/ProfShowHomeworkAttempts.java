/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.ExerciseInfo;

/**
 * @author Allison
 *
 */
public class ProfShowHomeworkAttempts {
	
	private int pid;
	private String cToken;
	private String cid;
	
	public ProfShowHomeworkAttempts(int profID, String token){
		this.pid = profID;
		this.cToken = token;
	}
	
	public boolean run(){
		//get cid from token
		try{
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt1 = conn.prepareStatement("SELECT C.cid FROM Course C WHERE (C.token = ?)");
			stmt1.setString(1, this.cToken);
	        ResultSet rs = stmt1.executeQuery();
	        rs.next();
	        this.cid = rs.getString("cid");
		}
		catch(Exception e){
			System.out.println("Error matching token to cid in ProfShowHomeworkAttempts: "+ e.getMessage());
		}
		
		int[] homeworkIDs = new int[0];
		String[] homeworkNames = new String[0];
		try{
			Connection conn = DBConnection.getConnection();
			//get number of homeworks for this course
			PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Course C, Exercise E " +
					"WHERE (C.token = ?) AND (C.cid = E.cid)");
			stmt1.setString(1, this.cToken);
	        ResultSet rs = stmt1.executeQuery();
	        if(rs.next()){
	        	int size =rs.getInt("COUNT(*)");
	        	homeworkIDs = new int[size];
	        	homeworkNames = new String[size];
	        	

	        	System.out.println("There are "+(homeworkIDs.length)+" homeworks for this course.");

	        	//Get list of exercise ids and names
	        	PreparedStatement stmt2 = conn.prepareStatement("SELECT E.eid, E.ename FROM Exercise E, Course C " +
	        			"WHERE (C.token = ?) AND (C.cid = E.cid)");
	        	stmt2.setString(1, this.cToken);
	        	rs = stmt2.executeQuery();
	        	int i = 0;
	        	while (rs.next()) {
	        		homeworkIDs[i] = rs.getInt("eid");
	        		homeworkNames[i] = rs.getString("ename");
	        		i++;
	        	}
	        }
	        else{
	        	System.out.println("Sorry, there are no homeworks added to this course.");
	        	return false;
	        }
		}
		catch(Exception e){
			System.out.println("Problem in getting list of homeworks for ProfShowHomeworkAttempts: "+ e.getMessage());
		}
		
		//for each exercise, get total number of attempts
	
		for(int i = 0;i<homeworkIDs.length; i++){
			ExerciseInfo[] hwInfo = new ExerciseInfo[numOfTotalExerciseAttempts(homeworkIDs[i])];
			int attemptCount = -1;
			try{
				Connection conn = DBConnection.getConnection();
				PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Attempt A " +
						"WHERE(A.eid = ? ) ");
				stmt1.setInt(1, homeworkIDs[i]);
		        ResultSet rs = stmt1.executeQuery();
		        while(rs.next()){
		        	attemptCount = rs.getInt("COUNT(*)");
		        }
			}
			catch(Exception e){
				System.out.println("Problem in getting number of total attempts for a homework in ProfShowHomeworkAttempts: "+ e.getMessage());
			}
			
			//find number of students that attempted each exercise
			int studentCount = -1;
			try{
				Connection conn = DBConnection.getConnection();
				PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Attempt A " +
						"WHERE(A.eid = ? ) GROUP BY A.sid");
				stmt1.setInt(1, homeworkIDs[i]);
		        ResultSet rs = stmt1.executeQuery();
		        while(rs.next()){
		        	studentCount = rs.getInt("COUNT(*)");
		        }
			}
			catch(Exception e){
				System.out.println("Problem in getting number of students who attempted the exercise in ProfShowHomeworkAttempts: "+ e.getMessage());
			}
			
			
			System.out.println(homeworkNames[i] + ": " +
					"\n   Average number of attempts: " + ((double)attemptCount/(double)studentCount));
			}
			
		
		return false;
	}
	/*
	 * returns the count of attempts made on a particular exercise
	 */
	private int numOfTotalExerciseAttempts(int eid){
		try{
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Attempt A " +
					"WHERE(A.eid = ? ) ");
			stmt1.setInt(1, eid);
	        ResultSet rs = stmt1.executeQuery();
	        if(rs.next()) return rs.getInt("COUNT(*)");
		}
		catch(Exception e){
			System.out.println("Problem in getting number of attempts for ProfShowHomeworkScores: "+ e.getMessage());
		}
		return 0;
	}
}
