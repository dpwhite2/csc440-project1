package edu.ncsu.csc.csc440.project1.menu;

public class StudentViewSinglePastSubmissionMenu extends Menu {
    
    private String sid;
    private String cid;
    private int attid;

    public StudentViewSinglePastSubmissionMenu(String sid, String cid, int attid) {
        this.sid = sid;
        this.cid = cid;
        this.attid = attid;
    }

    @Override
    public MenuChoice[] getChoices() throws Exception {
        // get questions
        return null;
    }

    @Override
    public boolean onChoice(MenuChoice choice) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

}
