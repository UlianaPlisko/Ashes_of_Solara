package com.example.mygame.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.mygame.exceptions.DataAccessException;
import com.example.mygame.models.Character;

/**
 * Data Access Object for managing Character entities in the database.
 */
public class CharacterDAO extends BaseDAO<Character> {

    @Override
    protected Character mapResultSet(ResultSet rs) throws SQLException {
        return new Character(
                rs.getInt("User_id"),
                rs.getString("Name"),
                rs.getInt("MaxHealth"),
                rs.getInt("Health"),
                rs.getInt("MaxSanity"),
                rs.getInt("Sanity"),
                rs.getInt("MaxHunger"),
                rs.getInt("Hunger"),
                rs.getInt("x"),
                rs.getInt("y")
        );
    }

    /**
     * Adds a new character to the database.
     *
     * @param character the character to add
     * @return the number of rows affected
     * @throws DataAccessException if the operation fails
     */
    public int addCharacter(Character character) {
        String query = "INSERT INTO \"Character\" (\"User_id\", \"Name\", \"MaxHealth\", \"Health\", \"MaxSanity\", \"Sanity\", \"MaxHunger\", \"Hunger\", \"x\", \"y\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(query,
                character.getUserId(),
                character.getName(),
                character.getMaxHealth(),
                character.getHealth(),
                character.getMaxSanity(),
                character.getSanity(),
                character.getMaxHunger(),
                character.getHunger(),
                character.getX(),
                character.getY());
    }

    /**
     * Deletes a character by its ID.
     *
     * @param id the ID of the character to delete
     * @return the number of rows affected
     * @throws DataAccessException if the operation fails
     */
    public int deleteCharacter(Long id) {
        String query = "DELETE FROM \"Character\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    /**
     * Retrieves a character by its ID.
     *
     * @param id the ID of the character
     * @return the character, or null if not found
     * @throws DataAccessException if the operation fails
     */
    public Character getCharacterById(Long id) {
        String query = "SELECT * FROM \"Character\" WHERE \"id\" = ?";
        List<Character> characters = findAll(query, id);
        return characters.isEmpty() ? null : characters.get(0);
    }

    /**
     * Updates a character by its ID.
     *
     * @param character the character with updated values
     * @return the number of rows affected
     * @throws DataAccessException if the operation fails
     */
    public int updateCharacter(Character character) {
        String query = "UPDATE \"Character\" SET \"User_id\" = ?, \"Name\" = ?, \"MaxHealth\" = ?, \"Health\" = ?, \"MaxSanity\" = ?, \"Sanity\" = ?, \"MaxHunger\" = ?, \"Hunger\" = ?, \"x\" = ?, \"y\" = ? WHERE \"User_id\" = ?";
        return executeUpdate(query,
                character.getUserId(),
                character.getName(),
                character.getMaxHealth(),
                character.getHealth(),
                character.getMaxSanity(),
                character.getSanity(),
                character.getMaxHunger(),
                character.getHunger(),
                character.getX(),
                character.getY(),
                character.getUserId());
    }

    /**
     * Retrieves all characters from the database.
     *
     * @return a list of all characters
     * @throws DataAccessException if the operation fails
     */
    public List<Character> getAllCharacters() {
        String query = "SELECT * FROM \"Character\"";
        return findAll(query);
    }
}
