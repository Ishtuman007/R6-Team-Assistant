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

            boolean foundOp = false;;
            for (Operator op : availableOperators) {
                if (op.getName().equalsIgnoreCase(name)) {
                    foundOp = true;
                    break;
                }
            }
            // Error statments.
            if (!foundOp) {
                System.out.println("Operator not found. Please try again.");
            } else if (bannedOperators.contains(name)) {
                System.out.println("Operator already banned. Please select a different operator.");
            } else {
                // Add the operator to the banned ArrayList.
                // Also store the spelling from the official database so it can be 
                // used to compare with the database later.
                for (Operator op : availableOperators) {
                    if (op.getName().equalsIgnoreCase(name)) {
                        bannedOperators.add(op.getName());
                        break;
                    }
                }
                System.out.println(name + " has been banned.");
            }
        }
        return bannedOperators;
    }
}
