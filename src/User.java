import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private int ID;
    private String firstName;
    private String lastName;
    private String password;
    private final Database database;

    public User(Database database) {
        this.database = database;
    }

    public void createAccount(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = passwordHashing(password);
        this.ID = insertUser();
    }

    public String passwordHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    private int insertUser() {
        int id = 0;

        String sql = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, PASSWORD) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }

            System.out.print("Account created successfully! Your ID: " + id);
        }
        catch(SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Unable to insert user", ex);
        }

        return id;
    }

    public void deleteAccount(String firstName, String lastName) {
        deleteUser(firstName, lastName);
    }

    private void deleteUser(String firstName, String lastName) {
        String sql = "DELETE FROM USERS WHERE FIRST_NAME = ? AND LAST_NAME = ?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            int removedRow = preparedStatement.executeUpdate();

            if(removedRow > 0) {
                System.out.println("Account removed successfully!");
            } else {
                System.out.println("No account found with the first and last name");
            }
        }
        catch(SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Unable to delete user", ex);
        }
    }
}