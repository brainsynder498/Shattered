package shattered.brainsynder.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bow.Bow;
import shattered.brainsynder.bow.BowManager;
import shattered.brainsynder.bow.list.FixerBow;
import shattered.brainsynder.cache.AbstractCacheView;
import shattered.brainsynder.cache.CacheView;
import shattered.brainsynder.modules.IModule;
import shattered.brainsynder.modules.list.GlassModule;
import shattered.brainsynder.player.PlayerManager;

public class BowListener extends IModule implements Listener {
    public CacheView<String, Bow> bowCache = null;
    public CacheView<String, Float> forceCache = null;

    public BowListener(Shattered shattered) {
        super(shattered, "BowListener");
    }

    @Override
    public void onLoad() {
        Bukkit.getPluginManager().registerEvents(this, getShattered());
        bowCache = new AbstractCacheView<>();
        forceCache = new AbstractCacheView<>();
    }

    @Override
    public void unLoad() {
        HandlerList.unregisterAll(this);
        bowCache = null;
        forceCache = null;
    }

    @EventHandler
    public void onFire(EntityShootBowEvent e) {
        BowManager manager = getShattered().getModuleManager().getModule("BowManager");
        if (manager.isBow(e.getBow())) {
            PlayerManager playerManager = getShattered().getModuleManager().getModule("PlayerManager");
            Bow bow = manager.getBow(e.getBow());
            if (!(e.getProjectile() instanceof Arrow)) return;
            Arrow arrow = (Arrow) e.getProjectile();
            if (arrow == null) return;
            if (arrow.getShooter() instanceof Player)
                bow.setShooter(playerManager.getPlayer((Player) arrow.getShooter()));
            bow.onLaunch(e.getEntity().getEyeLocation(), e.getForce());
            forceCache.inject(arrow.getUniqueId().toString(), e.getForce());
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
                    bow.whileInAir(arrow, e.getForce());
                }
            }.runTaskTimer(getShattered(), 0, 1);
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) e.getEntity();
        if ((!bowCache.hasKey(arrow.getUniqueId().toString()))
                && (!forceCache.hasKey(arrow.getUniqueId().toString()))) return;
        Bow bow = bowCache.get(arrow.getUniqueId().toString());
        if (arrow.hasMetadata("INSTA-KILL")) {
            if (e.getHitEntity() != null) {
                if (e.getHitEntity() instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) e.getHitEntity();
                    entity.damage(entity.getHealth(), bow.getShooter().getPlayer());
                }
            }
        }

        if (e.getHitBlock() != null) {
            if (bow != null)
                bow.onHit(e.getHitBlock().getLocation(), forceCache.get(arrow.getUniqueId().toString()));
            if (!(bow instanceof FixerBow)) {
                GlassModule glassModule = getShattered().getModuleManager().getModule("GlassModule");
                glassModule.onArrowLand(bow.getShooter().getArena(), e.getHitBlock());
            }
        }
        if (bow != null) if (e.getHitEntity() != null) bow.onHit(e.getHitEntity().getLocation());
        arrow.remove();
    }
}
