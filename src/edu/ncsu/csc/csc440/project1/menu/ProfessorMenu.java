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
    
    private int profid;
    
    public ProfessorMenu(int profid) {
        this.profid = profid;
    }
	
	/* (non-Javadoc)
	 * @see Menu#getChoices()
	 */
	public MenuChoice[] getChoices() {
        MenuChoice[] menuChoices = {
            new MenuChoice("S", "Select Course"),
            new MenuChoice("A", "Add Course"),
            new MenuChoice("X", "Back")
		};
		return menuChoices;
	}

	/* (non-Javadoc)
	 * @see Menu#onChoice(MenuChoice)
	 */
	public boolean onChoice(MenuChoice choice) throws Exception {
		// TODO finish
		
		if(choice.shortcut.equals("S")){
			ProfSelectCourseMenu menu1 = new ProfSelectCourseMenu();
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
