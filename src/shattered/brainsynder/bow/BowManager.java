package shattered.brainsynder.bow;

import org.bukkit.inventory.ItemStack;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bow.list.*;
import shattered.brainsynder.modules.IModule;
import shattered.brainsynder.utils.ItemUtils;
import shattered.brainsynder.utils.RandomCollection;

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

    public RandomCollection<Class<? extends Bow>> getSpecialBows() {
        RandomCollection<Class<? extends Bow>> bowCollection = new RandomCollection<>();
        for (Bow bow : bows) {
            if (!bow.getClass().isAssignableFrom(RegularBow.class)) {
                bowCollection.add(50, bow.getClass());
            }
        }
        return bowCollection;
    }

    public Bow getSpecialBow(Class<? extends Bow> bowClass) {
        if (bowClass.isAssignableFrom(FixerBow.class)) return new FixerBow(getShattered());
        if (bowClass.isAssignableFrom(DrunkerBow.class)) return new DrunkerBow(getShattered());
        if (bowClass.isAssignableFrom(RainmakerBow.class)) return new RainmakerBow(getShattered());
        if (bowClass.isAssignableFrom(GlassDestroyerBow.class)) return new GlassDestroyerBow(getShattered());
        return null;
    }

    public Bow getBow(ItemStack stack) {
        ItemUtils utils = getShattered().getModuleManager().getModule("ItemUtils");
        for (Bow bow : bows) {
            if (utils.isSimilar(bow.getItem(), stack)) {
                if (bow.getClass().isAssignableFrom(RegularBow.class)) return new RegularBow(getShattered());
                if (getSpecialBow(bow.getClass()) != null) return getSpecialBow(bow.getClass());
            }
        }
        return null;
    }

    @Override
    public void onLoad() {
        bows = new ArrayList<>();
        bows.add(new DrunkerBow(getShattered()));
        bows.add(new RainmakerBow(getShattered()));
        bows.add(new GlassDestroyerBow(getShattered()));
        bows.add(new RegularBow(getShattered()));
        bows.add(new FixerBow(getShattered()));
    }

    @Override
    public void unLoad() {
        bows.clear();
        bows = null;
    }
}
