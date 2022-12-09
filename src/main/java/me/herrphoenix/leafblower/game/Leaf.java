package me.herrphoenix.leafblower.game;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.engine.object.ui.Element;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

public class Leaf extends Element {
    private final Vector2f positionTarget;

    public Leaf(Vector2f position, Vector3f rotation) {
        super(LeafManager.getRandomLeafTexture(), position, rotation, new Vector3f(32, 32, 1f));
        this.positionTarget = new Vector2f();
    }

    @Override
    public void update() {
        for (LeafBlower leafBlower : Game.getInstance().getLeafBlowers()) {
            Vector2f blow = leafBlower.checkRange(this);
            if (blow == null) {
                continue;
            }

            this.positionTarget.x += leafBlower.getBlowRange() - blow.x / 3;
            this.positionTarget.y += leafBlower.getBlowRange() - blow.y / 3;
        }

        if (positionTarget.x != position.x || this.positionTarget.y != position.y) {
            float time = Game.getInstance().getDeltaTime() * 2;

//            this.position.x += Function.lerp(0, positionTarget.x, timeSquare * 50);
//            this.position.y += Function.lerp(0, positionTarget.y, timeSquare * 50);

            this.position.x += ((positionTarget.x * 50) * time * time);
            this.position.y += ((positionTarget.y * 50) * time * time);
        }

        super.update();
    }
}
