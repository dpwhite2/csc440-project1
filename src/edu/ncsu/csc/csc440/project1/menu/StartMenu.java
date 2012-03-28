package edu.ncsu.csc.csc440.project1.menu;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ncsu.csc.csc440.project1.db.DBConnection;
import edu.ncsu.csc.csc440.project1.objs.User;

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
    
    /**
     * Attempts to log user in.
     */
    User attemptLogin(String username, String password) throws Exception {
        // check username/password
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            /*PreparedStatement stmt1 = conn.prepareStatement("SELECT username, password, role FROM UserInfo");
            ResultSet rs1 = stmt1.executeQuery();
            while (rs1.next()) {
                String username1 = rs1.getString("username");
                String password1 = rs1.getString("password");
                System.out.printf("\"%s\", \"%s\"\n", username1, password1);
            }*/
            
            PreparedStatement stmt = conn.prepareStatement("SELECT userid, username, password, role FROM UserInfo WHERE username=?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                // username not found
                System.out.printf("Incorrect username or password.\n");
                //System.out.printf("username=\"%s\"\n", username);
                //System.out.printf("password=\"%s\"\n", password);
                return null;
            }
            int userid = rs.getInt("userid");
            String actual_username = rs.getString("username");
            String actual_password = rs.getString("password");
            String role = rs.getString("role");
            if (!password.equals(actual_password)) {
                // password does not match
                System.out.println("Incorrect username or password.");
                //System.out.printf("username=       \"%s\"\n", username);
                //System.out.printf("actual_username=\"%s\"\n", actual_username);
                //System.out.printf("password=       \"%s\"\n", password);
                //System.out.printf("actual_password=\"%s\"\n", actual_password);
                return null;
            }
            return new User(userid, actual_username, actual_password, role);
        } finally {
            conn.close();
        }
    }
    
    /**
     * @return Professor's pid
     */
    int loginAsProfessor(User user) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT pid FROM Professor P WHERE P.userid=?");
            stmt.setInt(1, user.getUserid());
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.printf("Error: Cannot find Professor with given userid.\n");
                return -1;
            }
            return rs.getInt("pid");
        } finally {
            conn.close();
        }
    }

    private String loginAsStudent(User user) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT sid FROM Student S WHERE S.userid=?");
            stmt.setInt(1, user.getUserid());
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.printf("Error: Cannot find Student with given userid.\n");
                return "";
            }
            return rs.getString("sid");
        } finally {
            conn.close();
        }
    }
    
    void login() throws Exception {
		Scanner scan = new Scanner(System.in);
        // prompt for username
        System.out.print("Your username: ");
        String username = scan.nextLine();
        // prompt for password
        System.out.print("Your password: ");
        String password = scan.nextLine();
        
        User user = attemptLogin(username, password);
        if (user == null) {
            return;
        }
        
        // check user role
        if (user.getRole().equals("student")) {
            System.out.println("Logging in as student...");
            String sid = loginAsStudent(user);
            if (!sid.equals("")) {
                StudentMenu sm = new StudentMenu(sid);
                sm.menuLoop();
            }
        } else if (user.getRole().equals("prof")) {
            System.out.println("Logging in as professor...");
            int pid = loginAsProfessor(user);
            if (pid != -1) {
                ProfessorMenu pm = new ProfessorMenu(pid);
                pm.menuLoop();
            }
        } else {
            // error
            System.out.println("Error logging in! Role not found.");
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
        
        stmt = conn.prepareStatement("SELECT userinfo_ids.currval FROM dual");
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            // TODO: error
            System.out.printf("Could not retrieve new key.\n");
            return;
        }
        int userid = rs.getInt(1);
        
        if (role.equals("student")) {
            // TODO
        } else if (role.equals("prof")) {
            stmt = conn.prepareStatement("INSERT INTO Professor VALUES (professor_ids.nextval,?)");
            stmt.setInt(1, userid);
            stmt.executeUpdate();
        } else {
            System.out.printf("Error. Unknown role!\n");
        }
    }
}


