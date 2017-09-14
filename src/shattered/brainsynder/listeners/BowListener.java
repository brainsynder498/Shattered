package shattered.brainsynder.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bows.Bow;
import shattered.brainsynder.bows.BowManager;
import shattered.brainsynder.cache.AbstractCacheView;
import shattered.brainsynder.cache.CacheView;
import shattered.brainsynder.modules.IModule;

public class BowListener extends IModule implements Listener {
    private CacheView<String, Bow> bowCache = null;

    public BowListener(Shattered shattered) {
        super(shattered, "BowListener");
    }

    @Override
    public void onLoad() {
        Bukkit.getPluginManager().registerEvents(this, getShattered());
        bowCache = new AbstractCacheView<>();
    }

    @Override
    public void unLoad() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onFire(EntityShootBowEvent e) {
        BowManager manager = getShattered().getModuleManager().getModule("BowManager");
        if (manager.isBow(e.getBow())) {
            Bow bow = manager.getBow(e.getBow());
            if (!(e.getProjectile() instanceof Arrow)) return;
            Arrow arrow = (Arrow) e.getProjectile();
            if (arrow == null) return;
            bowCache.inject(arrow.getUniqueId().toString(), bow);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (arrow.isDead()) {
                        cancel();
                        return;
                    }
                    if (!arrow.isValid()) {
                        cancel();
                        return;
                    }
                    bow.whileInAir(arrow);
                }
            }.runTaskTimer(getShattered(), 0, 1);
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) e.getEntity();
        if (arrow.hasMetadata("REMOVE_ME")) {
            arrow.remove();
            return;
        }
        if (!bowCache.hasKey(arrow.getUniqueId().toString())) return;
        Bow bow = bowCache.get(arrow.getUniqueId().toString());

        if (e.getHitBlock() != null) bow.onHit(e.getHitBlock().getLocation());
        if (e.getHitEntity() != null) bow.onHit(e.getHitEntity().getLocation());
        arrow.remove();
    }
}
