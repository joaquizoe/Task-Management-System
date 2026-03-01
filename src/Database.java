import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private Connection connection;

    public Database() {
        createConnection();
    }

    void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB", "root", "AkoSiSam2006");
        }
        catch(SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Database closing failed", ex);
        }
    }
}
