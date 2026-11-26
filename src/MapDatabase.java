import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapDatabase {
    public static ArrayList<String> loadMaps(String filename) {
        // This ArrayList will hold all the maps loaded from the CSV file.
        // I used ArrayList because it can change in size as new maps are added to the csv file database.
        ArrayList<String> maps = new ArrayList<>();

        // try with resources automatically closes the BufferedReader after use
        // and BufferedReader reads the file line by line.
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip the header line because that has the column names.
            br.readLine();

            String line;
            // reads every line one at a time until the end of the file.
            while ((line = br.readLine()) != null) {
                // separates the 2 different parts of the map information.
                String[] parts = line.split(",");

                // map name + map site.
                if (parts.length == 2) {
                    maps.add(parts[0] + " - " + parts[1]);
                }
            }
        } catch (IOException e) {
            // Catches unreadable files without crashing the program.
            System.out.println("Error loading maps: " + e.getMessage());
        }
        // complete database so other classes can use it.
        return maps;
    }
}