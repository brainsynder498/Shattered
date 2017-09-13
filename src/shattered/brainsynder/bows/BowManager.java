package shattered.brainsynder.bows;

import shattered.brainsynder.Shattered;
import shattered.brainsynder.bows.list.DrunkerBow;
import shattered.brainsynder.modules.IModule;

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

    @Override
    public void onLoad() {
        bows = new ArrayList<>();
        bows.add(new DrunkerBow());
    }

    @Override
    public void unLoad() {
        bows.clear();
        bows = null;
    }
}
