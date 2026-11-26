import java.util.ArrayList;
import java.util.Scanner;

public class Bans {
    public static ArrayList<String> selectBans(ArrayList<Operator> operators, String side, Scanner input) {
        // Stores the banned operator names.
        ArrayList<String> bannedOperators = new ArrayList<>();
        // Only shows operators from the selected side.
        ArrayList<Operator> availableOperators = new ArrayList<>();

        for (Operator op : operators) {
            if (op.getSide().equalsIgnoreCase(side)) {
                availableOperators.add(op);
            }
        }

        System.out.println("\nAvailable " + side + " Operators:");

        for (Operator op : availableOperators) {
            System.out.println("- " + op.getName());
        }
        // There can be up to 3 bans for each side/phase.
        while (bannedOperators.size() < 3) {
            System.out.print("\nEnter the banned operator by name (or type 'done' to finish): ");
            String name = input.nextLine();

            if (name.equalsIgnoreCase("done")) {
                break;
            }

            Operator selectedOp = null;
            for (Operator op : availableOperators) {
                if (op.getName().equalsIgnoreCase(name)) {
                    selectedOp = op;
                    break;
                }
            }

            // Error statments.
            if (selectedOp == null) {
                System.out.println("Operator not found. Please try again.");
            } else if (isAlreadyBanned(bannedOperators, selectedOp.getName())) {
                System.out.println("Operator already banned. Please select a different operator.");
            } else {
                // Store the spelling from the official database so it can be 
                // used to compare with the database later.
                bannedOperators.add(selectedOp.getName());
                System.out.println(selectedOp.getName() + " has been banned.");
            }
        }
        return bannedOperators;
    }

    // Compare without any case sensitivity because I found that error when testing.
    private static boolean isAlreadyBanned(ArrayList<String> bannedOperators, String name) {
        for (String banned : bannedOperators) {
            if (banned.equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }
}
