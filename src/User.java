import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private int ID;
    private String firstName;
    private String lastName;
    private String password;

    public void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first and last name: ");
        this.firstName = scanner.next();
        this.lastName = scanner.next();

        System.out.print("Choose a password: ");
        this.password = scanner.next();

        insertUser();
    }

    private void insertUser() {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, PASSWORD) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                int id = resultSet.getInt(1);
                this.ID = id;
            }

            System.out.print("Account created successfully! Your ID is : " + ID);
        }
        catch(SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Unable to insert user", ex);
        }
    }
}
