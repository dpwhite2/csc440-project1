/**
 * 
 */

/**
 * @author Allison
 *
 */
public class ProfSelectCourseMenu extends Menu{
	
	private String promptText; //might not need this?
	private MenuChoice[] menuChoices;
	
	/* (non-Javadoc)
	 * @see Menu#getChoices()
	 */
	public MenuChoice[] getChoices() {
		
		// TODO query database to find courses prof has, given prof ID
		/*
		 * SELECT Course.name FROM Course, Professor
		 * WHERE Professor.pid = Course.pid
		 */
		
		// TODO finish
		//for each choice in the ResultSet, 
		//make new MenuChoice, add new choice to menuChoices array,
		// and add the cid to cid[]
		
		
		return menuChoices;
	}

	/* (non-Javadoc)
	 * @see Menu#onChoice(MenuChoice)
	 */
	public boolean onChoice(MenuChoice choice) {
		// TODO finish
		
		for(int i =0; i< menuChoices.length;i++){
			if(choice.equals(menuChoices[i])){
				//make new ProfCourseMenu and pass it the cid from cid[i]
				break;
			}
		}
	
		return false;
	}
}
