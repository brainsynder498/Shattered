package shattered.brainsynder.player;

import org.bukkit.entity.Player;
import shattered.brainsynder.arena.Arena;

public interface IGamePlayer {
    Player getPlayer();

    StoredData getPlayerData();

    Arena getArena ();

    void setArena (Arena arena);

    boolean isPlaying();

    State getState();

    void setState(State state);

    enum State {
        NOT_PLAYING,
        IN_GAME,
        IN_GAME_ARENA,
        WAITING
    }
}
