package com.killerchess.core.game;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;

@Entity
@Table(name = "game_state")
public class GameState {

    @Column(name = "state")
    private String state;

    private GameStateIdentity gameStateIdentity;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @EmbeddedId
    public GameStateIdentity getGameStateIdentity() {
        return gameStateIdentity;
    }
    public void setGameStateIdentity(GameStateIdentity gameStateIdentity) {
        this.gameStateIdentity = gameStateIdentity;
    }

}
