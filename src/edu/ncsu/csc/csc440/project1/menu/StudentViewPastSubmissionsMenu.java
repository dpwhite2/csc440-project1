package edu.ncsu.csc.csc440.project1.menu;

public class StudentViewPastSubmissionsMenu extends Menu {
	
	private String sid;
	private String cid;

	public StudentViewPastSubmissionsMenu(String sid, String cid) {
		this.sid = sid;
		this.cid = cid;
	}

	@Override
	public MenuChoice[] getChoices() throws Exception {
		// Show all homeworks past due
		MenuChoice[] choices = {
			new MenuChoice("X", "Back")
		};
		return choices;
	}

	@Override
	public boolean onChoice(MenuChoice choice) throws Exception {
		if (choice.shortcut.equals("X")) {
			return false;
		} else {
			throw new RuntimeException("Should not get here.");
		}
	}

}
