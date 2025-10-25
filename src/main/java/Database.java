import io.github.cdimascio.dotenv.Dotenv;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    // Load the .env file
    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");;

    public static void saveTemperature(double celsius, double fahrenheit, Label statusLabel) {
        String sql = "INSERT INTO temperature_log (celsius, fahrenheit) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, celsius);
            stmt.setDouble(2, fahrenheit);
            stmt.executeUpdate();
            statusLabel.setText("Saved to database!");

        } catch (SQLException e) {
            statusLabel.setText("DB Error: " + e.getMessage());
        }
    }
}
