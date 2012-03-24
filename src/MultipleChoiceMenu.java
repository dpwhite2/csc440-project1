/**
 * 
 */

/**
 * @author Allison
 *
 */
public class MultipleChoiceMenu extends Menu{

	private String promptText;
	private MenuChoice[] menuChoices;
	//private Menu nextStep;
	
	public MultipleChoiceMenu(String text) {
		this.promptText = text;
	}
	
	public MultipleChoiceMenu(String text, MenuChoice[] choices) {
		this.promptText = text;
		this.menuChoices = choices;
	}

	public void setChoices(MenuChoice[] choices){
		this.menuChoices = choices;
	}
	public MenuChoice[] getChoices() {
		
		return menuChoices;
	}

	public boolean onChoice(MenuChoice choice) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
