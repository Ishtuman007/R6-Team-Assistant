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

        // A team has 5 operators so this will run until that condition is met or user is done.
        while (team.size() < 5) {
            System.out.print("\nSelect an operator by name (or type 'done' to finish): ");
            String selectedName = input.nextLine();
            // User can finish before 5 operators if they want to (2, 3, or 4 operator stacks for that many friends playing together).
            if (selectedName.equalsIgnoreCase("done")) {
                break;
            }
            // search for the operator without crweating a new object. 
            // This way the team list keeps the same database information and can be used by other classes.
            Operator selectedOperator = null;
            for (Operator operator : availableOperators) {
                if (operator.getName().equalsIgnoreCase(selectedName)) {
                    selectedOperator = operator;
                    break;
                }
            }
            if (selectedOperator == null) {
                System.out.println("Operator not found. Please try again.");
              // This checks that the same operator is not picked twice because that is not possible in the game. 
              // It also checks that the operator is actually in the available operators list.  
            } else if (team.contains(selectedOperator)) {
                System.out.println("Operator already selected. Please choose a different operator.");
            } else {
                team.add(selectedOperator);
                System.out.println(selectedOperator.getName() + " added to your team.");
            }
        }
        System.out.println("\nYour team:");
        // Display final team so the user can see and confirm.
        for (Operator operator : team) {
            System.out.println(" - " + operator.getName());
        }
        
        return team;
    }
}
