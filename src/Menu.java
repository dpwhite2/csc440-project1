/**
 * 
 */
import java.util.Scanner;
/**
 * @author Allison
 *
 */
public abstract class Menu {
	
	public Menu() {
		
	}
	
	/**
	 * @param text - The prompt text
	 * @param options - An array of the option available after using "fixOptions()"
	 * @return option chosen based on options imputed
	 */
	public int promptUser(String text, String[] options){
		Scanner scan = new Scanner(System.in);
		System.out.println(text + "\n");
		for(int i=0; i<options.length;i++){
			System.out.println(options[i]);
		}
		
		int choice = scan.nextInt();
		return choice;
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
     * The main menu loop. This is the public interface to this class.
     */
    public void menuLoop() {
        while (true) {
            MenuChoice[] choices = getChoices();
            if (choices.length == 0) {
                // TODO: throw exception
            }
            MenuChoice choice = null;
            // TODO: display choices
            while (true) {
                // TODO: prompt user
                char shortcut;
                for (MenuChoice cur: choices) {
                    if (cur.shortcut == shortcut) {
                        choice = cur;
                        break;
                    }
                }
                // if choice doesn't exist, print error and reprompt
                if (choice == null) {
                    // TODO: print error. While loop will go back and reprompt.
                } else {
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
    public abstract MenuChoice[] getChoices();
    
    /**
     * Called when a menu choice is made. Only valid choices are passed to this method.
     * @param choice 
     * @return true if the menu should continue, otherwise false
     */
    public abstract boolean onChoice(MenuChoice choice);

}
