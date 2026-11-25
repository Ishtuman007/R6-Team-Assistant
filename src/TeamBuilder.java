import java.util.ArrayList;
import java.util.Scanner;

public class TeamBuilder {
    public static ArrayList<Operator> selectOperators(ArrayList<Operator> operators, Scanner input) {
        // Stores the selected only operators for the team. This way the database can be used again.
        ArrayList<Operator> team = new ArrayList<>();

        System.out.println("\nChoose your side:");
        System.out.println("1. Attack");
        System.out.println("2. Defend");

        // user's choice.
        int choice = input.nextInt();
        input.nextLine();

        String side;
        // takes the user's choice and assigns the side. else statement also handles some invalid inputs.
        if (choice == 1) {
            side = "Attack";
        } else if (choice == 2) {
            side = "Defend";
        } else {
            System.out.println("Invalid choice.");
            return team;
        }

        // Makes a new list filtered by the choosen side. This way only that side's operators can be picked.
        ArrayList<Operator> availableOperators = new ArrayList<>();
        // ArrayList for loop to fill the availableOperators list.
        for (Operator operator : operators) {
            if (operator.getSide().equalsIgnoreCase(side)) {
                availableOperators.add(operator);
            }
        }
        System.out.println("\nAvailable " + side + " Operators:");
        // This one prints ont the available operators list with their role and utility.
        for (Operator operator : availableOperators) {
            System.out.println(operator.getName() + " | Role: " + operator.getRole() + " | Utility: " + operator.getUtility());
        }


        return team;
    }
}
