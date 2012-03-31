/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * @author Allison
 *
 */
public class ProfQuery {

	private int pid;
	private String cToken;
	
	public ProfQuery(int profID, String courseToken){
		this.pid = profID;
		this.cToken = courseToken;
		
	}
	
	public boolean run(){
		//get cid from token
		String cid = "";
		try{
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt1 = conn.prepareStatement("SELECT C.cid FROM Course C WHERE (C.token = ?)");
			stmt1.setString(1, this.cToken);
	        ResultSet rs = stmt1.executeQuery();
	        rs.next();
	        cid = rs.getString("cid");
		}
		catch(Exception e){
			System.out.println("Error matching token to cid in ProfQuery: "+ e.getMessage());
		}
		//get query from user
		System.out.println("Your pid is: " + this.pid +"\nThe cid for this course is: " + cid);
		String profQuery = promptUser("Please enter your SQL query below:\n\n");
		
		//run query
		try{
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt1 = conn.prepareStatement(profQuery);
        ResultSet rs = stmt1.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();
        int columnNum = meta.getColumnCount();  
      //make column headings
    	String header = "";
    	for(int i = 1; i<= columnNum; i++){
    		String name = meta.getColumnName(i);
    		header = header + " " + name + "  ";
    	}
    	System.out.println(header);
    	for(int i = 0; i<header.length();i++){
    		System.out.print("-");
    	}
    	System.out.print("\n");
    	
        //for each result
        while(rs.next()){
       
        	//for each column
        	for(int i = 1; i<= columnNum; i++){
        		String name = meta.getColumnName(i);
        		String type = meta.getColumnTypeName(i);
        		if(type.equals("VARCHAR") || type.equals("VARCHAR2")) System.out.print(rs.getString(name));
        		else if (type.equals("INTEGER") || type.equals("NUMBER")) System.out.print(rs.getInt(name));
        		else if(type.equals("DATE")) System.out.print(rs.getDate(name));
        		else{
        			System.out.println("Column type: " + type);
        			throw new RuntimeException("Error matching column types in ProfQuery");
        		}
        		System.out.print(" | ");
        	}
        	System.out.print("\n");
        }
		}
		catch(Exception e){
			System.out.println("Error printing out results: "+ e.getMessage());
		}
		return false;
		
	}
	
	public String promptUser(String prompt) {
        System.out.print(prompt);
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
}
