package com.killerchess.core.services;

import com.killerchess.core.exceptions.GameNotFoundException;
import com.killerchess.core.game.Game;
import com.killerchess.core.game.GameState;
import com.killerchess.core.repositories.GameRepository;
import com.killerchess.core.repositories.GameStateRepository;
import com.killerchess.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameStateRepository gameStateRepository;

    @Autowired
    public GameService(UserRepository userRepository, GameRepository gameRepository,
                       GameStateRepository gameStateRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameStateRepository = gameStateRepository;
    }

    public void initNewGame(String gameId, String gameName, String host) {
        var game = new Game();
        game.setGameId(gameId);
        game.setGameName(gameName);
        game.setHost(userRepository.findByLogin(host));
        gameRepository.save(game);
    }

    public void saveSpecificGameState(String gameId, String gameStateStr) throws GameNotFoundException {
        Game game = gameRepository.findByGameId(gameId);

        if (game == null) {
            throw new GameNotFoundException("Game with ID: " + gameId + " not found in database.");
        }

        GameState gameState = new GameState();
        gameState.setState(gameStateStr);
        gameState.setGame(game);

        gameStateRepository.save(gameState);
    }

}
