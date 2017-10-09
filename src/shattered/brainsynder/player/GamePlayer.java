package shattered.brainsynder.player;

import org.bukkit.entity.Player;
import shattered.brainsynder.arena.Arena;

class GamePlayer implements IGamePlayer {
    private Player player;
    private Arena arena;
    private State state = State.NOT_PLAYING;
    private StoredData storedData;

    GamePlayer(Player player) {
        this.player = player;
        storedData = new StoredData(player);
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public StoredData getPlayerData() {
        return storedData;
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    @Override
    public boolean isPlaying() {
        return ((arena != null) && ((state == State.IN_GAME_ARENA) || (state == State.IN_GAME)));
    }
}