
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
        while (choice != 6) {
            // Main menu
            System.out.println("\n1. Manual Team Builder");
            System.out.println("2. Random Team Generator");
            System.out.println("3. View Saved Teams");
            System.out.println("4. Load Saved Team");
            System.out.println("5. Delete Saved Team");
            System.out.println("6. Exit");

            System.out.print("\nSelect an option: ");
            choice = input.nextInt();
            input.nextLine();

            // Handle user's choice
            switch (choice) {
                case 1: {

                    String selectedMap = chooseMap(maps, input);
                    System.out.println("Selected map/site: " + selectedMap);

                    ArrayList<String> bannedOperators = new ArrayList<>();

                    System.out.println("\nSelect Attack bans:");
                    // removes the input operators to the correct side bans.
                    bannedOperators.addAll(Bans.selectBans(operators, "Attack", input));

                    System.out.println("\nSelect Defense bans:");
                    bannedOperators.addAll(Bans.selectBans(operators, "Defend", input));

                    // Remove banned operators before selecting teams.
                    ArrayList<Operator> availableOperators = filterBannedOperators(operators, bannedOperators);

                    ArrayList<Operator> team = TeamBuilder.selectOperators(availableOperators, input);

                    // Analyze the selected team and suggest another operator. 
                    // Doing the 'team building' part.
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

                    // Ask if the user wants to save the completed team if it is a full team.
                    if (team.size() == 5) {
                        askToSaveTeam(team, input);
                    }

                    break;
                }

                case 2: {

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
                    // Sides can switch so asking for both makes sense for late game situations.
                    ArrayList<String> bannedOperators = new ArrayList<>();

                    System.out.println("\nSelect Attack bans:");
                    bannedOperators.addAll(Bans.selectBans(operators, "Attack", input));

                    System.out.println("\nSelect Defense bans:");
                    bannedOperators.addAll(Bans.selectBans(operators, "Defend", input));

                    // Generate a random team using only available operators.
                    ArrayList<Operator> team = RandomTeamGenerator.generateRandomTeam(operators, side, bannedOperators);

                    TeamAnalyzer.analyzeTeam(team);

                    // Ask if the user wants to save the completed team if the team complete.
                    if (team.size() == 5) {
                        askToSaveTeam(team, input);
                    }

                    break;
                }

                case 3:
                    // Show all the teams that have been saved to the file so far.
                    SaveLoadManager.viewSavedTeams();
                    break;

                case 4: {
                    // Show saved teams so the user knows which team can be loaded.
                    SaveLoadManager.viewSavedTeams();

                    System.out.print("\nEnter the name of the team to load: ");
                    String teamName = input.nextLine();

                    // Load the selected team using the operator database.
                    ArrayList<Operator> loadedTeam = SaveLoadManager.loadTeam(teamName, operators);

                    // Analyze the team after it has been loaded.
                    if (!loadedTeam.isEmpty()) {
                        TeamAnalyzer.analyzeTeam(loadedTeam);
                    }

                    break;
                }

                case 5: {
                    // Show saved teams before asking which one to delete.
                    SaveLoadManager.viewSavedTeams();

                    System.out.print("\nEnter the name of the team to delete: ");
                    String teamName = input.nextLine();

                    // Remove the selected team from the saved teams file.
                    SaveLoadManager.deleteTeam(teamName);
                    break;
                }

                case 6:
                    System.out.println("Thanks for using the R6 Team Assistant!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        input.close();
    }

    // Ask the user if they want to save their completed team.
    private static void askToSaveTeam(ArrayList<Operator> team, Scanner input) {
        System.out.print("\nWould you like to save this team? (yes/no): ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            System.out.print("Enter a name for this team: ");
            String teamName = input.nextLine();

            // Save the team under the name given by the user.
            SaveLoadManager.saveTeam(teamName, team);
        }
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

        // Makes sure an invalid selection does not crash the project.
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
