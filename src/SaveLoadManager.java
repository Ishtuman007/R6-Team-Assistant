
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SaveLoadManager {

    private static final String FILE_NAME = "data/saved-teams.txt";

    // Save a complete team under the user's name that they choose.
    public static void saveTeam(String teamName, ArrayList<Operator> team) {

        if (teamName.equals("") || teamName.contains("|")) {
            System.out.println("Please enter a valid team name.");
            return;
        }

        if (team.size() != 5) {
            System.out.println("Only a complete teams of 5 operators can be saved.");
            return;
        }

        // All operators in a saved team need to be on the same side.
        String side = team.get(0).getSide();

        for (Operator op : team) {
            if (!op.getSide().equalsIgnoreCase(side)) {
                System.out.println("All operators must be on the same side.");
                return;
            }
        }

        try {
            File file = getSavedTeamsFile();

            // Do not let two saved teams have the same name.
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 7 && parts[0].equalsIgnoreCase(teamName)) {
                    br.close();
                    System.out.println("A saved team with that name already exists.");
                    return;
                }
            }

            br.close();

            // Store the team name, side, and the operator names in one line. 
            // File writer appends saved teams and writes in file (similar to pw).
            FileWriter fw = new FileWriter(file, true);

            fw.write(teamName + "|" + side);

            for (Operator op : team) {
                fw.write("|" + op.getName());
            }

            fw.write("\n");
            fw.close();

            System.out.println("Team saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving team: " + e.getMessage());
        }
    }

    // Display all the saved team names, sides, and operators to show the user.
    public static void viewSavedTeams() {

        try {
            File file = getSavedTeamsFile();
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            boolean foundTeam = false;

            System.out.println("\n========== SAVED TEAMS ==========");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 7) {
                    foundTeam = true;

                    System.out.println("\nTeam Name: " + parts[0]);
                    System.out.println("Side: " + parts[1]);
                    System.out.println("Operators:");

                    for (int i = 2; i < parts.length; i++) {
                        System.out.println("- " + parts[i]);
                    }
                }
            }

            if (!foundTeam) {
                System.out.println("No saved teams found.");
            }

            System.out.println("=================================");

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading saved teams: " + e.getMessage());
        }
    }

    // Make and store the team in another Operator list using the new saved names and the operator database.
    public static ArrayList<Operator> loadTeam(String teamName, ArrayList<Operator> operators) {

        ArrayList<Operator> team = new ArrayList<>();

        try {
            File file = getSavedTeamsFile();
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 7 && parts[0].equalsIgnoreCase(teamName)) {

                    // Match each saved name to the full operator from the database 
                    // to make sure they exist and the saved team was made correctly.
                    for (int i = 2; i < parts.length; i++) {
                        boolean foundOperator = false;

                        for (Operator op : operators) {
                            if (op.getName().equalsIgnoreCase(parts[i]) && op.getSide().equalsIgnoreCase(parts[1])) {

                                team.add(op);
                                foundOperator = true;
                                break;
                            }
                        }

                        if (!foundOperator) {
                            team.clear();
                            System.out.println("A saved operator from this team is not found.");
                            br.close();
                            return team;
                        }
                    }

                    br.close();
                    System.out.println("Team loaded successfully!");
                    return team;
                }
            }

            br.close();
            System.out.println("Saved team not found.");

        } catch (IOException e) {
            System.out.println("Error loading team: " + e.getMessage());
        }

        return team;
    }

    // Delete option for a saved team while keeping all the other saved teams.
    public static void deleteTeam(String teamName) {

        try {
            File file = getSavedTeamsFile();
            ArrayList<String> savedLines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            boolean deleted = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 7 && parts[0].equalsIgnoreCase(teamName)) {
                    deleted = true;
                } else {
                    savedLines.add(line);
                }
            }

            br.close();

            // Rewrite the file without the selected team because this will be what is shown to the user after delete.
            FileWriter fw = new FileWriter(file, false);

            for (String savedLine : savedLines) {
                fw.write(savedLine + "\n");
            }

            fw.close();

            if (deleted) {
                System.out.println("Team deleted successfully!");
            } else {
                System.out.println("Saved team not found.");
            }

        } catch (IOException e) {
            System.out.println("Error deleting team: " + e.getMessage());
        }
    }

    // Create the storage file automatically for the first time 
    // it is needed to be used to save a team.
    private static File getSavedTeamsFile() throws IOException {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }
}
