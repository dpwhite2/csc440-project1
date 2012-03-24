

public class MenuChoice {
    public String shortcut;
    public String description;
    
    public MenuChoice(String shortcut, String description) {
        this.shortcut = shortcut;
        this.description = description;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof MenuChoice) {
            MenuChoice c = (MenuChoice) obj;
            if (this.shortcut.equals(c.shortcut) &&
                this.description.equals(c.description))
            {
                return true;
            }
        }
        return false;
    }
}

