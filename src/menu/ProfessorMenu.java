/**
 * 
 */

/**
 * @author Allison
 *
 */
public class ProfessorMenu extends Menu{

	private String promptText; //might not need this?
	private MenuChoice[] menuChoices;
	
	/* (non-Javadoc)
	 * @see Menu#getChoices()
	 */
	public MenuChoice[] getChoices() {
		
		MenuChoice selectCourse = new MenuChoice("S", "Select Course");
		MenuChoice addCourse = new MenuChoice("A", "Add Course");
		MenuChoice back = new MenuChoice("X", "Back");
		
		menuChoices[0] = selectCourse;
		menuChoices[1] = addCourse;
		menuChoices[2] = back;
		
		return menuChoices;
	}

	/* (non-Javadoc)
	 * @see Menu#onChoice(MenuChoice)
	 */
	public boolean onChoice(MenuChoice choice) {
		// TODO finish
		
		if(choice.equals(menuChoices[0])){
			ProfSelectCourseMenu menu1 = new ProfSelectCourseMenu();
			menu1.menuLoop();
		}
		else if(choice.equals(menuChoices[1])){
			//make ProfAddCourseMenu
		}
		else if(choice.equals(menuChoices[2])){
			return false;
		}
		else{
			throw new RuntimeException("ProcessorMenu.onChoice() couldn't match choices");
		}
		return false;
	}

}
