/**
 * 
 */
package edu.ncsu.csc.csc440.project1.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ncsu.csc.csc440.project1.db.DBConnection;

/**
 * @author Allison
 *
 */
public class ProfSelectHomeworkMenu extends Menu{

		private int pid;
		private String cToken;
		private MenuChoice[] menuChoices;
		
	public ProfSelectHomeworkMenu(int profID, String token){
		pid = profID;
		cToken = token;
	}

	public MenuChoice[] getChoices() throws Exception {
		
		try{
			//find all HW for this course
			Connection conn = DBConnection.getConnection();
			//get number of HW for this course
			PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(*) FROM Exercise, Course C WHERE (Exercise.cid=C.cid) " +
					"AND (C.token = ?)");
			stmt1.setString(1, this.cToken);
	        ResultSet rs = stmt1.executeQuery();
	        
	        if(rs.next()) menuChoices = new MenuChoice[rs.getInt("COUNT(*)")+1];
	        else{
	        	menuChoices = new MenuChoice[1];
	        }
	        System.out.println("There are "+(menuChoices.length-1)+" HWs.");
	        
	        //Get list of HW names
			PreparedStatement stmt2 = conn.prepareStatement("SELECT eid, ename FROM Exercise, Course C WHERE (Exercise.cid=C.cid) " +
					"AND (C.token = ?)");
	        stmt2.setString(1, this.cToken);
	        rs = stmt2.executeQuery();
	        int i = 0;
	        while (rs.next()) {
	        	int eID = rs.getInt("eid");
	            String eName = rs.getString("ename");
	            menuChoices[i]= new MenuChoice(Integer.toString(eID), eName);
	            i++;
	        }
	        menuChoices[i]= new MenuChoice("X", "Back");
	        
			}
			catch(Exception e){
				System.out.println("Problem in creating choices for ProfSelectHomeworkMenu: "+ e.getMessage());
			}
			
			return menuChoices;
	}

	public boolean onChoice(MenuChoice choice) throws Exception {
		if(choice.shortcut.equals("X")) return false;
		else{
			for(int i =0; i< menuChoices.length;i++){
				if(choice.shortcut.equals(menuChoices[i].shortcut)){
					ProfEditHomeworkMenu menu = new ProfEditHomeworkMenu(Integer.parseInt(choice.shortcut));
					while(!menu.run());
					this.menuLoop(); //on next menu "back", run this menu again
					break;
				}
			}
		}
	
		return false;
	}
	
	
}
