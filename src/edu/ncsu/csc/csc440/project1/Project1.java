package edu.ncsu.csc.csc440.project1;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

public class Project1 {
    public static void main(String[] args) throws Exception {
        System.out.printf("Creating DBConnection...\n");
        DBConnection conn = new DBConnection();
        System.out.printf("Created DBConnection!\n");
    }
}
