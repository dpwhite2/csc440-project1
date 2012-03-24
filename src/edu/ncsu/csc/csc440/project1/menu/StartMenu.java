package edu.ncsu.csc.csc440.project1.menu;

public class StartMenu extends Menu {
    public MenuChoice[] getChoices() {
        MenuChoice[] choices = {
            new MenuChoice("L", "Login"),
            new MenuChoice("U", "Create User"),
            new MenuChoice("X", "Exit Program")
        };
        return choices;
    }
    
    public boolean onChoice(MenuChoice choice) {
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
    
    void login() {
        // prompt for username
        // prompt for password
        // check username/password
        // check user role
    }
    
    void createUser() {
        // prompt for user role
        // prompt for user name
        // prompt for user password
        // create user
    }
}


