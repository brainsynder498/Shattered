package shattered.brainsynder.player;

import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.modules.IModule;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager extends IModule {
    public PlayerManager(Shattered shattered) {
        super(shattered, "PlayerManager");
    }

    private Map<String, IGamePlayer> playerMap = null;

    @Override
    public void onLoad() {
        playerMap = new HashMap<>();
    }

    public IGamePlayer getPlayer (Player player) {
        if (playerMap.containsKey(player.getUniqueId().toString()))
            return playerMap.get(player.getUniqueId().toString());
        IGamePlayer gamePlayer = new GamePlayer(player);
        return playerMap.put(player.getUniqueId().toString(), gamePlayer);
    }

    @Override
    public void unLoad() {
        playerMap.clear();
        playerMap = null;
    }
}
