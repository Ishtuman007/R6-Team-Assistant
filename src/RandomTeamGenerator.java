import java.util.ArrayList;
import java.util.Random;

public class RandomTeamGenerator {
    public static ArrayList<Operator> generateRandomTeam(ArrayList<Operator> operators, String side, ArrayList<String> bannedOperators) {
    
        // Available operators based on the selected side and banned operators.
        ArrayList<Operator> availableOperators = new ArrayList<>();

        for (Operator op : operators) {
            if (op.getSide().equalsIgnoreCase(side) && !isBanned(bannedOperators, op.getName())) {
                availableOperators.add(op);
            }
        }
        ArrayList<Operator> team = new ArrayList<>();
        ArrayList<String> selectedRoles = new ArrayList<>(); 
        // Store duplicate role candidates instead of just getting rid of them.
        // They might be need to complete the team later.
        ArrayList<Operator> repeatedRoleOperators = new ArrayList<>();
        Random rand = new Random();

        // Need at least 5 available operators to form a full team.
        if (availableOperators.size() < 5) {
            System.out.println("Not enough available operators to create a full team.");
            return team;
        }

        // Randomly select 5 unique operators for the team.
        while (team.size() < 5 && !availableOperators.isEmpty()) {
            int randomIndex = rand.nextInt(availableOperators.size());
            Operator candidate = availableOperators.remove(randomIndex);

            // Try and get operators with new roles first if possible for a more balanced team.
            // Cannot add same operator again.
            if (!containsRole(selectedRoles, candidate.getRole())) {
                team.add(candidate);
                selectedRoles.add(candidate.getRole());
            } else {
                // Keep this candidate available for filling a remaining spot.
                repeatedRoleOperators.add(candidate);
            }
        }

        // Add the skipped candidates back so repeated roles can complete the team when needed.
        availableOperators.addAll(repeatedRoleOperators);

         // Fill in any remaining spots even if a role is repeated.
        while (team.size() < 5 && !availableOperators.isEmpty()) {
            int randomIndex = rand.nextInt(availableOperators.size());
            Operator candidate = availableOperators.remove(randomIndex);
            team.add(candidate);
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

    // Check roles without case sensitivity.
    private static boolean containsRole(ArrayList<String> roles, String role) {
        for (String selectedRole : roles) {
            if (selectedRole.equalsIgnoreCase(role)) {
                return true;
            }
        }

        return false;
    }

    // Use case insensitive matching so bans are applied right.
    private static boolean isBanned(ArrayList<String> bannedOperators, String name) {
        for (String banned : bannedOperators) {
            if (banned.equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }
}
