import java.util.ArrayList;

public class TeamAnalyzer {
    public static void analyzeTeam(ArrayList<Operator> team) {
        // These lists track the unique roles and utility types present in the team.
        // This way repeated ones can be found.
        ArrayList<String> roles = new ArrayList<>();
        ArrayList<String> utility = new ArrayList<>();

        System.out.println("\n========== TEAM ANALYSIS ==========");
        if (team.isEmpty()) {
            System.out.println("No operators selected.");
            System.out.println("==================================");
            return;
        }
        // Displays the team size and collects information about unique roles and utility types.
        for (Operator op : team) {
            System.out.println(op.getName() + " | Role: " + op.getRole() + " | Utility: " + op.getUtility());
            // Onyl add a role once so I can see which roles are missing.
            if (!containsIgnoreCase(roles, op.getRole())) {
                roles.add(op.getRole());
            }

            // Each time Utility occurs count so overlapping ones can be counted.
            utility.add(op.getUtility());
        }

        System.out.println("\nTeam Size: " + team.size());

        System.out.println("\nRoles Covered: ");
        for (String role : roles) {
            System.out.println("- " + role);
        }

        String side = team.get(0).getSide();
        // Compare roles present to the roles used in the project 
        // Will grow as operators are added to the database
        String[] impRoles;
        if (side.equalsIgnoreCase("Attack")) {
            impRoles = new String[] {"Entry", "Breacher", "Hard Breacher", "Support", "Intel", "Shield"};
        } else {
            impRoles = new String[] {"Support", "Area Denial", "Trap", "Anti-Breach", "Intel"};
        }

        System.out.println("\nPotentially Missing Roles: ");
        boolean missingRole = false;
        for (String role : impRoles) {
            if (!containsIgnoreCase(roles, role)) {
                System.out.println("- " + role);
                missingRole = true;
            }
        }

        if (!missingRole) {
            System.out.println("No missing roles! Your team is already balanced.");
        }

        // Count the repeated utility types to find overlapping ones.
        System.out.println("\nOverlapping Utility: ");
        boolean overlapUtil = false;

        for (int i = 0; i < utility.size(); i++) {
            String util = utility.get(i);
            int count = 0;
            // Count how many operators have this utility.
            for (int j = 0; j < utility.size(); j++) {
                if (util.equalsIgnoreCase(utility.get(j))) {
                    count++;
                }
            }
            // Print the repeated utilities only one time.
            if (count > 1) {
                boolean alreadyPrinted = false;

                for (int j = 0; j < i; j++) {
                    if (util.equalsIgnoreCase(utility.get(j))) {
                        alreadyPrinted = true;
                        break;
                    }
                }

                if (!alreadyPrinted) {
                    System.out.println("- " + util + " appears " + count + " times.");
                    overlapUtil = true;
                }
            }
        }

        if (!overlapUtil) {
            System.out.println("No overlapping utility! Your team has a great variety.");
        }

        System.out.println("==================================");
    }

    // Reuse the comparison so roles are checked no matter the capitalization.
    private static boolean containsIgnoreCase(ArrayList<String> values, String target) {
        for (String value : values) {
            if (value.equalsIgnoreCase(target)) {
                return true;
            }
        }
        return false;
    }
}
