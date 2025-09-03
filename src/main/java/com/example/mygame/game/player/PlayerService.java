package com.example.mygame.game.player;

import com.example.mygame.dao.CharacterDAO;
import com.example.mygame.models.Character;

public class PlayerService {
    private final CharacterDAO characterDAO = new CharacterDAO();

    public Character getCharacterForUser(int userId) {
        // assuming userId = character.userId
        return characterDAO.getCharacterById((long) userId);
    }

    public void saveCharacter(Character character) {
        characterDAO.updateCharacter(character);
    }
}
