/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

/**
 * @author Allison
 *
 */
public class ProfReportsMenu extends Menu{
	
	private int pid;
	private String cToken;
	private MenuChoice[] menuChoices = {new MenuChoice("G", "Student Grade"),
			 new MenuChoice("S", "Homework Score Stats"), new MenuChoice("A", "Homework Attempt Stats"),
			 new MenuChoice("Q", "Query"),new MenuChoice("X", "Back")};
	
	public ProfReportsMenu(int profID, String token){
		this.pid = profID;
		this.cToken = token;
	}
	
	public MenuChoice[] getChoices() throws Exception {
		
		return this.menuChoices;
	}

	public boolean onChoice(MenuChoice choice) throws Exception {
		//TODO finish
		if(choice.shortcut.equals("G")){
			ProfShowStudentGrades gradesMenu = new ProfShowStudentGrades(this.pid,this.cToken);
			gradesMenu.run();
			this.menuLoop();
		}
		else if(choice.shortcut.equals("S")){
			//ProfShowHomeworkScores
			this.menuLoop();
		}
		else if(choice.shortcut.equals("A")){
			//ProfShowHomeworkAttempts
			this.menuLoop();
		}
		else if(choice.shortcut.equals("Q")){
			ProfQuery queryMenu = new ProfQuery(this.pid, this.cToken);
			queryMenu.run();
			this.menuLoop();
		}
		else if(choice.shortcut.equals("X")){
			return false;
		}
		else{
			throw new RuntimeException("ProfReportsMenu.onChoice() couldn't match choices");
		}
		return false;
	}

}
