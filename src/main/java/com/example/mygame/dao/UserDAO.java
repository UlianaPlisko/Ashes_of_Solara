package com.example.mygame.dao;

import com.example.mygame.exceptions.DataAccessException;
import com.example.mygame.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object for managing User entities in the database.
 */
public class UserDAO extends BaseDAO<User> {

    @Override
    protected User mapResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("Usernamebigint"),
                rs.getString("Email"),
                rs.getString("Password")
        );
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the user to add
     * @return the number of rows affected
     * @throws DataAccessException if the operation fails
     */
    public int addUser(User user) {
        String query = "INSERT INTO \"User\" (\"id\", \"Usernamebigint\", \"Email\", \"Password\") VALUES (?, ?, ?, ?)";
        return executeUpdate(query,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @return the number of rows affected
     * @throws DataAccessException if the operation fails
     */
    public int deleteUser(Long id) {
        String query = "DELETE FROM \"User\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user
     * @return the user, or null if not found
     * @throws DataAccessException if the operation fails
     */
    public User getUserById(Long id) {
        String query = "SELECT * FROM \"User\" WHERE \"id\" = ?";
        List<User> users = findAll(query, id);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Updates a user by its ID.
     *
     * @param user the user with updated values
     * @return the number of rows affected
     * @throws DataAccessException if the operation fails
     */
    public int updateUser(User user) {
        String query = "UPDATE \"User\" SET \"Usernamebigint\" = ?, \"Email\" = ?, \"Password\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getId());
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users
     * @throws DataAccessException if the operation fails
     */
    public List<User> getAllUsers() {
        String query = "SELECT * FROM \"User\"";
        return findAll(query);
    }
}
