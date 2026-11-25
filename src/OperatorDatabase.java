import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OperatorDatabase {
    public static ArrayList<Operator> loadOperators(String filename) {
        // This ArrayList will hold all the operators loaded from the CSV file.
        // I used ArrayList also because it can change in size as new operators are added to the csv file database.
        ArrayList<Operator> operators = new ArrayList<>();

        // try with resources automatically closes the BufferedReader after use
        // and BufferedReader reads the file line by line.
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip the header line because that has the column names.
            br.readLine();

            String line;
            // reads every line one at a time until the end of the file.
            while ((line = br.readLine()) != null) {
                // separates the 4 different parts of the operator information.
                String[] parts = line.split(",");

                // This new Operator object can be used by the rest of the program to access that operators information.
                Operator operator = new Operator(parts[0], parts[1], parts[2], parts[3]);
                operators.add(operator);
            }
        } catch (IOException e) {
            // Catches unreadable files without crashing the program.
            System.out.println("Error loading operators: " + e.getMessage());
        }
        // complete database so other classes can use it.
        return operators;
    }
}
