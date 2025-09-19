package com.example.mygame.dao;

import com.example.mygame.utils.exceptions.DataAccessException;
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
                rs.getString("Username"),
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
        String query = "INSERT INTO \"User\" (\"Username\", \"Email\", \"Password\") VALUES (?, ?, ?)";
        return executeUpdate(query,
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
    }

    /**
     * Deletes a user by its ID.
     *
     * @return the number of rows affected
     * @throws DataAccessException if the operation fails
     */
    public int deleteUser(String username) {
        String query = "DELETE FROM \"User\" WHERE \"Username\" = ?";
        return executeUpdate(query, username);
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
        String query = "UPDATE \"User\" SET \"Username\" = ?, \"Email\" = ?, \"Password\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
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

    /**
     * Retrieves a user by its username.
     *
     * @param username the username to search for
     * @return the user, or null if not found
     * @throws DataAccessException if the operation fails
     */
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM \"User\" WHERE \"Username\" = ?";
        List<User> users = findAll(query, username);
        return users.isEmpty() ? null : users.get(0);
    }

    public int getIdByUsername(String username) {
        String query = "SELECT \"id\" FROM \"User\" WHERE \"Username\" = ?";
        Integer id = findSingleValue(query, username);
        return id != null ? id : -1;
    }
}
