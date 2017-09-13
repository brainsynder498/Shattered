package shattered.brainsynder.modules;

import shattered.brainsynder.Shattered;

public abstract class IModule {
    private Shattered shattered = null;
    private String name;

    public IModule (Shattered shattered, String name){
        this.shattered = shattered;
        this.name = name;
    }

    public void onLoad () {}
    public void unLoad () {}

    protected Shattered getShattered() {
        return shattered;
    }

    protected String getName() {
        return name;
    }
}
