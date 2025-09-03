package com.example.mygame.login;

import com.example.mygame.dao.CharacterDAO;
import com.example.mygame.dao.UserDAO;
import com.example.mygame.exceptions.DataAccessException;
import com.example.mygame.models.User;
import com.example.mygame.models.Character;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;
    private final CharacterDAO characterDAO;
    private static final int MIN_PASSWORD_LENGTH = 8;

    public UserService() {
        this.userDAO = new UserDAO();
        this.characterDAO = new CharacterDAO();
    }

    /**
     * Authenticates a user by checking username and password.
     *
     * @param username the username to check
     * @param password the password to verify
     * @return the authenticated User object, or null if authentication fails
     * @throws IllegalArgumentException if input validation fails
     * @throws DataAccessException if database access fails
     */
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        User user = userDAO.getUserByUsername(username); // Assuming a new method in UserDAO
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            return null; // Invalid credentials
        }
        return user;
    }

    /**
     * Registers a new user with a hashed password.
     *
     * @param username the desired username
     * @param email the user's email
     * @param password the password to hash
     * @return the created User object
     * @throws IllegalArgumentException if input validation fails
     * @throws DataAccessException if database access fails
     */
    public User register(String username, String email, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (userDAO.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, email, hashedPassword);
        userDAO.addUser(user);

        // Get the user from DB (to fetch generated ID)
        int userId = userDAO.getIdByUsername(username);

        // Create and insert default Character
        Character character = new Character(
                userId,      // set User_id
                username,      // character name
                100, 100,                // maxHealth, health
                100, 100,                // maxSanity, sanity
                100, 100,                // maxHunger, hunger
                0, 0                     // x, y position
        );

        characterDAO.addCharacter(character);
        return user;
    }

    public int getUserId(User user) {
        return userDAO.getIdByUsername(user.getUsername());
    }
}
