package edu.ncsu.csc.csc440.project1.menu;

public class StudentAttemptHomeworkSelectMenu extends Menu {
	
	private String sid;
	private String cid;

	public StudentAttemptHomeworkSelectMenu(String sid, String cid) {
		this.sid = sid;
		this.cid = cid;
	}

	@Override
	public MenuChoice[] getChoices() throws Exception {
		// Show all open homeworks.
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
