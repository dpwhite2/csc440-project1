package edu.ncsu.csc.csc440.project1.menu;

/**
 * 
 */
import java.util.Scanner;

/**
 * @author Allison
 *
 */
public abstract class Menu {
	/**
	 * @param text - The prompt text
	 * @param options - An array of the option available after using "fixOptions()"
	 * @return option chosen based on options imputed
	 */
	public String promptUser(String prompt) {
        System.out.print(prompt);
		Scanner scan = new Scanner(System.in);
		return scan.nextLine().toUpperCase();
	}
	
	/**
	 * @param options - The array of possible choices, not including a "back" option or new line characters
	 * @return An array of all choices (with the back option) formatted correctly
	 */
	public String[] fixOptions(String[] options){
		String[]tempOptions = new String[options.length+1];
		tempOptions[0] = "Back";
		for(int i=0; i<options.length;i++){
			tempOptions[i+1] = options[i];
		}
		for(int i=0; i<tempOptions.length;i++){
			tempOptions[i] = i + "-" + tempOptions[i];
		}
		
		return tempOptions;
	}
    
    /**
     *
     */
    public String promptMsg() {
        return "Please choose one: ";
    }
    
    /**
     *
     */
    public String invalidChoiceMsg(String shortcut) {
        return "Unrecognized choice: \"" + shortcut + "\". Please try again.";
    }
    
    /**
     *
     */
    public void displayChoices(MenuChoice[] choices) {
        for (MenuChoice choice: choices) {
            System.out.printf("%s: %s\n", choice.shortcut, choice.description);
        }
    }
    
    /**
     * The main menu loop. This is the public interface to this class.
     */
    public void menuLoop() throws Exception {
        while (true) {
            MenuChoice[] choices = getChoices();
            if (choices.length == 0) {
                throw new RuntimeException("getChoices() returned empty list of choices.");
            }
            displayChoices(choices);
            
            // Get the user's menu choice
            MenuChoice choice = null;
            while (true) {
                String shortcut = promptUser(promptMsg());
                // Check that choice is a valid one.
                for (MenuChoice cur: choices) {
                    if (cur.shortcut.equals(shortcut)) {
                        choice = cur;
                        break;
                    }
                }
                // if choice doesn't exist, print error and reprompt
                if (choice == null) {
                    // Print error. While loop will go back and reprompt.
                    System.out.println(invalidChoiceMsg(shortcut));
                } else {
                    // Choice does exist, so exit this inner while-loop.
                    break;
                }
            }
            
            // Execute menu choice
            boolean continue_loop = onChoice(choice);
            if (!continue_loop) {
                break;
            }
        }
    }
    
    /** 
     * Get an array of all choices for this menu.  This is called each time the 
     * menu is displayed, so it can change dynamically.
     */
    public abstract MenuChoice[] getChoices() throws Exception;
    
    /**
     * Called when a menu choice is made. Only valid choices are passed to this method.
     * @param choice 
     * @return true if the menu should continue, otherwise false
     */
    public abstract boolean onChoice(MenuChoice choice) throws Exception;

}
