package shattered.brainsynder.arena;

import shattered.brainsynder.utils.Cuboid;

public class Arena {
    private String id;
    private Cuboid cuboid;

    public Arena (String id) {
        this.id = id;
    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public String getId() {
        return id;
    }
}
