import java.util.ArrayList;
public class OperatorRecommender {
    public static Operator recommendOperator(ArrayList<Operator> operators, ArrayList<Operator> team, String side, ArrayList<String> bannedOperators) {
        Operator bestOp = null;
        int highestScore = -1;

        // These ArrayLists track the roles and utility your teammates already give
        // so that the recommended operator can fill in the gaps in your team.
        ArrayList<String> teamRoles = new ArrayList<>();
        ArrayList<String> teamUtility = new ArrayList<>();

        for (Operator op : team) {
            teamRoles.add(op.getRole());
            teamUtility.add(op.getUtility());
        }

        // Now I have to compare every available operator because the best choice 
        // depends on the current team and that changes every time.
        for (Operator op : operators) {

            // Skip operators that are not on the selected side, banned, or already in the team.
            if (!op.getSide().equalsIgnoreCase(side) || bannedOperators.contains(op.getName()) || team.contains(op)) {
                continue; 
            }

            int score = 0;

            // Add to the score if op adds a role the team is missing 
            // (more points than utility because roles are more important).
            if (!teamRoles.contains(op.getRole())) {
                score += 3; // Role is more important
            }
            // Add to the score if op adds a utility the team is missing.
            // worth less than role.
            if (!teamUtility.contains(op.getUtility())) {
                score += 2; 
            }
            // Update the best operator as you go.
            if (score > highestScore) {
                highestScore = score;
                bestOp = op;
            }
        }
        return bestOp;
    }
}
