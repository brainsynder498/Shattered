package shattered.brainsynder.bows;

import org.bukkit.inventory.ItemStack;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bows.list.DrunkerBow;
import shattered.brainsynder.bows.list.ExplosiveBow;
import shattered.brainsynder.bows.list.RainmakerBow;
import shattered.brainsynder.modules.IModule;
import shattered.brainsynder.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class BowManager extends IModule {
    private List<Bow> bows = null;

    public BowManager(Shattered shattered) {
        super(shattered, "BowManager");
    }

    public List<Bow> getBows() {
        return bows;
    }

    public boolean isBow(ItemStack stack) {
        ItemUtils utils = getShattered().getModuleManager().getModule("ItemUtils");
        for (Bow bow : bows) if (utils.isSimilar(bow.getItem(), stack)) return true;
        return false;
    }

    public Bow getBow(ItemStack stack) {
        ItemUtils utils = getShattered().getModuleManager().getModule("ItemUtils");
        for (Bow bow : bows) {
            if (utils.isSimilar(bow.getItem(), stack)) {
                if (bow.getClass().isAssignableFrom(DrunkerBow.class)) return new DrunkerBow (getShattered());
                if (bow.getClass().isAssignableFrom(RainmakerBow.class)) return new RainmakerBow (getShattered());
                if (bow.getClass().isAssignableFrom(ExplosiveBow.class)) return new ExplosiveBow (getShattered());
            }
        }
        return null;
    }

    @Override
    public void onLoad() {
        bows = new ArrayList<>();
        bows.add(new DrunkerBow(getShattered()));
        bows.add(new RainmakerBow(getShattered()));
        bows.add(new ExplosiveBow(getShattered()));
    }

    @Override
    public void unLoad() {
        bows.clear();
        bows = null;
    }
}
