import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<String> maps = MapDatabase.loadMaps("data/maps.csv");

        for (String map : maps) {
            System.out.println(map);
        }
        
        // gets the list of operators from the csv file.
        ArrayList<Operator> operators = OperatorDatabase.loadOperators("data/operators.csv");
        int choice = 0;
        // Welcome message
        System.out.println("==================================");
        System.out.println(" RAINBOW SIX SIEGE TEAM ASSISTANT");
        System.out.println("==================================");

        System.out.println("Build balanced teams and find the");
        System.out.println("best operator for every situation.");

        while (choice != 3) {
            // Main menu
            System.out.println("\n1. Manual Team Builder");
            System.out.println("2. Random Team Generator");
            System.out.println("3. Exit");

            System.out.print("\nSelect an option: ");
            choice = input.nextInt();

            // Handle user choice
            switch (choice) {
                case 1:
                    // Call the manual team builder method
                    ArrayList<Operator> team =
                    TeamBuilder.selectOperators(operators, input);
                    break;
                case 2:
                    // Call the random team generator method
                    //randomTeamGenerator();
                    System.out.println("Random Team Generator is not implemented yet.");
                    break;
                case 3:
                    System.out.println("Thanks for using the R6 Team Assistant!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        input.close();
    }
}