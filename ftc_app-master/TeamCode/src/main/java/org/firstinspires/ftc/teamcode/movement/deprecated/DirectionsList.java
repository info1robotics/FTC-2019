package org.firstinspires.ftc.teamcode.movement.deprecated;

import java.util.ArrayList;

@Deprecated
public class DirectionsList extends ArrayList {

    private final int SIZE_LIMIT = 10;

    public DirectionsList() {
        super();
    }

    public boolean add(Direction d) {
        if (this.size() == this.SIZE_LIMIT) {
            this.remove(0);
        }
        return super.add(d);
    }

    public boolean stayedConstant() {
        Direction firstDirection = (Direction) this.get(0);
        for (int i=1; i < Math.min(this.SIZE_LIMIT, this.size()); i++) {
            Direction d = (Direction) this.get(i);
            if (!firstDirection.is_close(d)) {
                return false;
            }
        }
        return true;
    }
}
