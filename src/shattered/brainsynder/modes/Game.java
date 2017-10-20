package shattered.brainsynder.modes;


import org.bukkit.event.Listener;
import shattered.brainsynder.arena.Arena;
import shattered.brainsynder.player.IGamePlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class Game<T> implements Listener {
    public List<IGamePlayer> players;
    public List<IGamePlayer> deadPlayers;
    private Arena arena = null;

    public Game(Arena arena) {
        this.arena = arena;
        players = new ArrayList<>();
        deadPlayers = new ArrayList<>();
    }

    public int minPlayers() {
        return 2;
    }

    /**
     * Run on Game end
     */
    public abstract void onEnd();

    public abstract void respawnPlayer(IGamePlayer gamePlayer);

    public abstract void lost(T player);

    public void onLeave(IGamePlayer player) {
    }

    public int aliveCount() {
        return (players.size() - deadPlayers.size());
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public abstract void onWin(T gamePlayer);

    /**
     * Run on Game Start
     */
    public void onStart() {

    }

    /**
     * Runs per tick
     */
    public abstract void perTick();

    /**
     * Has the game started?
     */
    public abstract boolean hasStarted();

    /**
     * Set if the game has started
     */
    public abstract void setStarted(boolean val);

    /**
     * the Players Currently in the Game
     */
    public List<IGamePlayer> getPlayers() {
        return players;
    }

    /**
     * Add a player to the Game
     */
    public void addPlayer(IGamePlayer player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    /**
     * Remove a player to the Game
     */
    public void removePlayer(IGamePlayer player) {
        if (players.contains(player)) {
            players.remove(player);
        }
    }

    public boolean isSetup() {
        return false;
    }

    public boolean allowsPVP() {
        return false;
    }

    public String[] description() {
        return new String[0];
    }

    public Arena getArena() {
        return arena;
    }
}
