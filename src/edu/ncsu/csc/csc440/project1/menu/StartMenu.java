package edu.ncsu.csc.csc440.project1.menu;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

public class StartMenu extends Menu {
    public MenuChoice[] getChoices() {
        MenuChoice[] choices = {
            new MenuChoice("L", "Login"),
            new MenuChoice("U", "Create User"),
            new MenuChoice("X", "Exit Program")
        };
        return choices;
    }
    
    public boolean onChoice(MenuChoice choice) throws Exception {
        if (choice.shortcut.equals("L")) {
            login();
            return true;
        } else if (choice.shortcut.equals("U")) {
            createUser();
            return true;
        } else if (choice.shortcut.equals("X")) {
            return false;
        }
        throw new RuntimeException("Should not get here.");
    }
    
    void login() throws Exception {
		Scanner scan = new Scanner(System.in);
        // prompt for username
        System.out.print("Your username: ");
        String username = scan.nextLine();
        // prompt for password
        System.out.print("Your password: ");
        String password = scan.nextLine();
        
        // check username/password
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT username, password, role FROM UserInfo WHERE username=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            // username not found
            // TODO: print error message
            return;
        }
        String actual_username = rs.getString("username");
        String actual_password = rs.getString("password");
        if (!password.equals(actual_password)) {
            // password does not match
            // TODO: print error message
            return;
        }
        // check user role
        String role = rs.getString("role");
        if (role.equals("student")) {
            
        } else if (role.equals("prof")) {
            
        } else {
            // error
        }
    }
    
    void createUser() throws Exception {
        // prompt for user role
        // prompt for user name
        // prompt for user password
        // create user
    }
}


