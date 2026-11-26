import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
         // gets the list of operators and maps from the csv file.
        ArrayList<String> maps = MapDatabase.loadMaps("data/maps.csv");
        ArrayList<Operator> operators = OperatorDatabase.loadOperators("data/operators.csv");

        int choice = 0;
        // Welcome message
        System.out.println("==================================");
        System.out.println(" RAINBOW SIX SIEGE TEAM ASSISTANT");
        System.out.println("==================================");

        System.out.println("Build balanced teams and find the");
        System.out.println("best operator for every situation.");
        
        // Run until they exit.
        while (choice != 3) {
            // Main menu
            System.out.println("\n1. Manual Team Builder");
            System.out.println("2. Random Team Generator");
            System.out.println("3. Exit");

            System.out.print("\nSelect an option: ");
            choice = input.nextInt();

            // Handle user's choice
            switch (choice) {
                case 1: {

                    input.nextLine();

                    String selectedMap = chooseMap(maps, input);
                    System.out.println("Selected map/site: " + selectedMap);

                    ArrayList<String> bannedOperators = new ArrayList<>();

                    System.out.println("\nSelect Attack bans:");
                    // removes the input operators to the correct side bans.
                    bannedOperators.addAll(Bans.selectBans(operators, "Attack", input));

                    System.out.println("\nSelect Defense bans:");
                    bannedOperators.addAll(Bans.selectBans(operators, "Defend", input));

                    // Remove banned operators before team selection.
                    ArrayList<Operator> availableOperators = filterBannedOperators(operators, bannedOperators);

                    ArrayList<Operator> team = TeamBuilder.selectOperators(availableOperators, input);

                    // Analyze the selected team and suggest another operator.
                    TeamAnalyzer.analyzeTeam(team);

                    if (!team.isEmpty()) {
                        Operator recommendation = OperatorRecommender.recommendOperator(
                                        operators, team, team.get(0).getSide(), bannedOperators);

                        if (recommendation != null) {
                            System.out.println("\nRecommended Operator: " + recommendation.getName());
                        } else {
                            System.out.println("\nNo available recommendations.");
                        }
                    }

                    break;
                }

                case 2: {
                    
                    input.nextLine();

                    String selectedMap = chooseMap(maps, input);
                    System.out.println("Selected map/site: " + selectedMap);

                    System.out.println("\nChoose your side:");
                    System.out.println("1. Attack");
                    System.out.println("2. Defend");
                    System.out.print("Selection: ");

                    int sideC = input.nextInt();
                    input.nextLine();

                    String side;

                    if (sideC == 1) {
                        side = "Attack";
                    } else if (sideC == 2) {
                        side = "Defend";
                    } else {
                        System.out.println("Invalid side choice.");
                        break;
                    }

                    // Get bans for both sides before generating the team.
                    ArrayList<String> bannedOperators = new ArrayList<>();

                    System.out.println("\nSelect Attack bans:");
                    bannedOperators.addAll(Bans.selectBans(operators, "Attack", input));

                    System.out.println("\nSelect Defense bans:");
                    bannedOperators.addAll(Bans.selectBans(operators, "Defend", input));

                    // Generate a random team using only available operators.
                    ArrayList<Operator> team = RandomTeamGenerator.generateRandomTeam(operators, side, bannedOperators);

                    TeamAnalyzer.analyzeTeam(team);

                    break;
                }
                case 3:
                    System.out.println("Thanks for using the R6 Team Assistant!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        input.close();
    }

    private static String chooseMap(ArrayList<String> maps, Scanner input) {
        System.out.println("\nAvailable Maps and Bomb Sites:");

        for (int i = 0; i < maps.size(); i++) {
            System.out.println((i + 1) + ". " + maps.get(i));
        }

        if (maps.isEmpty()) {
            return "No map selected";
        }

        System.out.println("Choose a map/site number: ");
        int selection = input.nextInt();
        input.nextLine();

        // Invalid selection does not crash the project.
        if (selection < 1 || selection > maps.size()) {
            System.out.println("Invalid selection.");
            return "No map selected";
        }

        return maps.get(selection - 1);
    }

    // Make a filtered list so banned operators cannot be selected for manual mode.
    private static ArrayList<Operator> filterBannedOperators(ArrayList<Operator> operators, ArrayList<String> bannedOperators) {

        ArrayList<Operator> availableOperators = new ArrayList<>();

        for (Operator op : operators) {
            if (!bannedOperators.contains(op.getName())) {
                availableOperators.add(op);
            }
        }

        return availableOperators;
    }
}