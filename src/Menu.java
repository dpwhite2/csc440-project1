/**
 * 
 */
import java.util.Scanner;
/**
 * @author Allison
 *
 */
public class Menu {
	
	public Menu(){
		
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

}
