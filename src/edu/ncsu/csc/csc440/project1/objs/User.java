package edu.ncsu.csc.csc440.project1.objs;

public class User {
    private int userid;
    private String username;
    private String password;
    private String role;
    
    public User() {
        userid = -1;
        username = "";
        password = "";
        role = "";
    }
    
    public User(int userid, String username, String password, String role) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public int getUserid() { return userid; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    
}

