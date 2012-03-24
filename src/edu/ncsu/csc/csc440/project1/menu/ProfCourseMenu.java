/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

/**
 * @author Allison
 *
 */
public class ProfCourseMenu extends Menu{

	private MenuChoice[] menuChoices = {new MenuChoice("A", "Add homework"),
			new MenuChoice("E", "Edit homework"), new MenuChoice("Q", "Add question"),
			new MenuChoice("N", "Add answer"), new MenuChoice("R", "Reports"),
			new MenuChoice("X", "Back")};;
	
	/* (non-Javadoc)
	 * @see Menu#getChoices()
	 */
	public MenuChoice[] getChoices() {
		
		return this.menuChoices;
	}

	
	public boolean onChoice(MenuChoice choice) {
		// TODO finish
		if(choice.shortcut.equals("A")){
			//make ProfAddHomework obj
		}
		else if(choice.shortcut.equals("E")){
			//ProfSelectHomeworkMenu
		}
		else if(choice.shortcut.equals("Q")){
			//ProfSelectQuestionTopicMenu
		}
		else if(choice.shortcut.equals("N")){
			//ProfAddAnswerMenu
		}
		else if(choice.shortcut.equals("R")){
			//ProfReportsMenu
		}
		else if(choice.equals("X")){
			return false;
		}
		else{
			throw new RuntimeException("ProfCourseMenu.onChoice() couldn't match choices");
		}
		return false;
	}
	
}
