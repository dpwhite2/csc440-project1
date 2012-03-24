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
        PreparedStatement stmt1 = conn.prepareStatement("SELECT username, password, role FROM UserInfo");
        ResultSet rs1 = stmt1.executeQuery();
        while (rs1.next()) {
            String username1 = rs1.getString("username");
            String password1 = rs1.getString("password");
            System.out.printf("\"%s\", \"%s\"\n", username1, password1);
        }
        
        PreparedStatement stmt = conn.prepareStatement("SELECT username, password, role FROM UserInfo WHERE username=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            // username not found
            // TODO: print error message
            System.out.printf("Incorrect username or password.\n");
            System.out.printf("username=\"%s\"\n", username);
            System.out.printf("password=\"%s\"\n", password);
            return;
        }
        String actual_username = rs.getString("username");
        String actual_password = rs.getString("password");
        if (!password.equals(actual_password)) {
            // password does not match
            // TODO: print error message
            System.out.println("Incorrect username or password.");
            System.out.printf("username=       \"%s\"\n", username);
            System.out.printf("actual_username=\"%s\"\n", actual_username);
            System.out.printf("password=       \"%s\"\n", password);
            System.out.printf("actual_password=\"%s\"\n", actual_password);
            return;
        }
        // check user role
        String role = rs.getString("role");
        if (role.equals("student")) {
            System.out.println("Logging in as student...");
        } else if (role.equals("prof")) {
            System.out.println("Logging in as professor...");
        } else {
            // error
            System.out.println("Error logging in!");
        }
    }
    
    void createUser() throws Exception {
		Scanner scan = new Scanner(System.in);
        // prompt for user role
        String role = null;
        while (true) {
            System.out.print("The user's role (prof, student): ");
            role = scan.nextLine();
            if (role.equals("prof") || role.equals("student")) {
                break;
            } else {
                System.out.println("The role must be: prof or student.");
            }
        }
        // prompt for user name
        System.out.print("The user's username: ");
        String username = scan.nextLine();
        // prompt for user password
        System.out.print("The user's password: ");
        String password = scan.nextLine();
        
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO UserInfo VALUES (userinfo_ids.nextval,?,?,?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, role);
        stmt.executeUpdate();
    }
}


