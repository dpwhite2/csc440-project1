

public class MenuChoice {
    public char shortcut;
    public String description;
    
    public boolean equals(Object obj) {
        if (obj instanceof MenuChoice) {
            MenuChoice c = (MenuChoice) obj;
            if (this.shortcut == c.shortcut && 
                this.description.equals(c.description))
            {
                return true;
            }
        }
        return false;
    }
}

