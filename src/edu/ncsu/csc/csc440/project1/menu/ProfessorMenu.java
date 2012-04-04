package edu.ncsu.csc.csc440.project1.menu;

/**
 * 
 */

/**
 * @author Allison
 *
 */
public class ProfessorMenu extends Menu{

	private int pid;
	
	public ProfessorMenu(int id){
		this.pid = id;
	}
	
	public MenuChoice[] getChoices() {
        MenuChoice[] menuChoices = {
            new MenuChoice("S", "Select Course"),
            new MenuChoice("A", "Add Course"),
            new MenuChoice("X", "Back")
		};
		return menuChoices;
	}

	
	public boolean onChoice(MenuChoice choice) throws Exception {

		if(choice.shortcut.equals("S")){
			ProfSelectCourseMenu menu1 = new ProfSelectCourseMenu(this.pid);
			menu1.menuLoop();
		}
		else if(choice.shortcut.equals("A")){
			ProfAddCourseMenu menu2 = new ProfAddCourseMenu(this.pid);
			while(!menu2.run()); //run until it returns true (successful)
		}
		else if(choice.shortcut.equals("X")){
			return false;
		}
		else{
			throw new RuntimeException("ProcessorMenu.onChoice() couldn't match choices");
		}
		this.menuLoop();
		return false;
	}

}
