// This class represents a Rainbow Six Siege operator
public class Operator {
    private String name;
    private String side;
    private String role;
    private String utility;
    
    public Operator(String name, String side, String role, String utility) {
        this.name = name;
        this.side = side;
        this.role = role;
        this.utility = utility;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getUtility() {
        return utility;
    }
    public String getSide() {
        return side;
    }
}
