package edu.ncsu.csc.csc440.project1;

import java.sql.Connection;
import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.menu.StartMenu;

public class Project1 {
    public static void main(String[] args) throws Exception {
        StartMenu menu = new StartMenu();
        menu.menuLoop();
        /*System.out.printf("Creating DBConnection...\n");
        Connection conn = DBConnection.getConnection();
        System.out.printf("Created DBConnection!\n");*/
    }
}
