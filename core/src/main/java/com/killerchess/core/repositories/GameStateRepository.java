package com.killerchess.core.repositories;

import com.killerchess.core.game.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStateRepository extends JpaRepository<GameState, Integer> {
//    GameState findByGameId(Integer id);
}
