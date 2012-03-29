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
			
	private String cToken;
	private int pid;
	
	public ProfCourseMenu(int profID,String courseToken){
		this.cToken = courseToken;
		this.pid = profID;
	}
	
	/* (non-Javadoc)
	 * @see Menu#getChoices()
	 */
	public MenuChoice[] getChoices() {
		
		return this.menuChoices;
	}

	
	public boolean onChoice(MenuChoice choice) throws Exception{
		// TODO finish
		if(choice.shortcut.equals("A")){
			ProfAddHomework addMenu = new ProfAddHomework(this.pid,this.cToken);
			while(!addMenu.run());
		}
		else if(choice.shortcut.equals("E")){
			ProfSelectHomeworkMenu selectMenu = new ProfSelectHomeworkMenu(this.pid, this.cToken);
			selectMenu.menuLoop();
			this.menuLoop();
		}
		else if(choice.shortcut.equals("Q")){
			ProfSelectQuestionTopicMenu topicMenu = new ProfSelectQuestionTopicMenu(this.pid, this.cToken);
			topicMenu.menuLoop();
			this.menuLoop();
		}
		else if(choice.shortcut.equals("N")){
			ProfAddAnswerMenu ansMenu = new ProfAddAnswerMenu(this.cToken);
			ansMenu.menuLoop();
		}
		else if(choice.shortcut.equals("R")){
			//ProfReportsMenu
		}
		else if(choice.shortcut.equals("X")){
			return false;
		}
		else{
			throw new RuntimeException("ProfCourseMenu.onChoice() couldn't match choices");
		}
		return false;
	}
	
}
