package com.killerchess.core.game;

import com.killerchess.core.user.User;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    private String gameId;
    private String gameName;
    private User host;
    private User guest;
    private boolean gameFinished = false;

    @Id
    @Column(name = "game_id")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String id) {
        this.gameId = id;
    }

    @Column(name = "game_name")
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String name) {
        this.gameName = name;
    }

    @ManyToOne
    @JoinColumn(name = "host")
    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    @ManyToOne
    @JoinColumn(name = "guest")
    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    @Column(name = "game_finished")
    public boolean getGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return gameId.equals (((Game) o).gameId);
    }
}