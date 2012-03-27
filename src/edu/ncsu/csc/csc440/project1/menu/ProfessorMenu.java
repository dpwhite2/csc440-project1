package edu.ncsu.csc.csc440.project1.menu;

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
	private int pid;
	
	public ProfessorMenu(int id){
		this.pid = id;
	}
	
	
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
	public boolean onChoice(MenuChoice choice) throws Exception {
			
		if(choice.shortcut.equals("S")){
			ProfSelectCourseMenu menu1 = new ProfSelectCourseMenu(this.pid);
			menu1.menuLoop();
		}
		else if(choice.shortcut.equals("A")){
			ProfAddCourseMenu menu2 = new ProfAddCourseMenu();
			while(!menu2.run()); //run until it returns true
		}
		else if(choice.shortcut.equals("X")){
			return false;
		}
		else{
			throw new RuntimeException("ProcessorMenu.onChoice() couldn't match choices");
		}
		return false;
	}

}
