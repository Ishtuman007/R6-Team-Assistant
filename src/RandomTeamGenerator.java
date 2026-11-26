import java.util.ArrayList;
import java.util.Random;

public class RandomTeamGenerator {
    public static ArrayList<Operator> generateRandomTeam(ArrayList<Operator> operators, String side, ArrayList<String> bannedOperators) {
    
        // Available operators based on the selected side and banned operators.
        ArrayList<Operator> availableOperators = new ArrayList<>();

        for (Operator op : operators) {
            if (op.getSide().equalsIgnoreCase(side) && !bannedOperators.contains(op.getName())) {
                availableOperators.add(op);
            }
        }
        ArrayList<Operator> team = new ArrayList<>();
        ArrayList<String> selectedRoles = new ArrayList<>(); 
        Random rand = new Random();

        // Need at least 5 available operators to form a full team.
        if (availableOperators.size() < 5) {
            System.out.println("Not enough available operators to create a full team.");
            return team;
        }

        // Randomly select 5 unique operators for the team.
        while (team.size() < 5 && !availableOperators.isEmpty()) {
            Operator candidate = availableOperators.get(rand.nextInt(availableOperators.size()));

            // Remove to make sure a new operator is picked.
            availableOperators.remove(candidate);

            // Try and get operators with new roles first if possible for a more balanced team.
            // Cannot add same operator again.
            if (!team.contains(candidate)) {
                // new role or the last selection can repick a role if needed to complete the team.
                if (!selectedRoles.contains(candidate.getRole()) || team.size() == 4) {

                    team.add(candidate);

                    if (!selectedRoles.contains(candidate.getRole())) {
                        selectedRoles.add(candidate.getRole());
                    }
                }
            } 
        }
        // Only possible now but if I add more operators this would never happen.
        if (team.size() < 5) {
            System.out.println("Could not generate a balanced team of five.");
            return team;
        }
        // Can see the generated team right away without looking at the returned ArrayList;
        System.out.println("\n========== RANDOM TEAM ==========");
        System.out.println("Side: " + side);

        for (Operator op : team) {
            System.out.println("- " + op.getName() + " | Role: " + op.getRole() + " | Utility: " + op.getUtility());
        }

        System.out.println("=================================");

        // can pass to TeamAnalyzer other other things in main.
        return team;
    }
}
