package me.herrphoenix.leafblower.game;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.engine.object.ui.Element;
import me.herrphoenix.leafblower.io.MouseCursor;
import me.herrphoenix.leafblower.io.TextureLoader;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

public class LeafBlower extends Element {
    private final float blowRange;

    public LeafBlower(Vector2f position, float blowRange) {
        super(TextureLoader.loadTexture("/textures/debug.png", true), position,
                new Vector3f(), new Vector3f(blowRange, blowRange, 1));
        this.blowRange = blowRange;
    }

    public Vector2f checkRange(Leaf leaf) {
        Vector2f leafPosition = leaf.getPosition();

        double xDist = leafPosition.x - position.x;
        double yDist = leafPosition.y - position.y;
        double xyDist = Math.pow(xDist, 2) + Math.pow(yDist, 2);

        if (Math.sqrt(xyDist) < blowRange) {
            return new Vector2f((float) xDist, (float) yDist);
        }
        return null;
    }

    @Override
    public void update() {
        position.x = (float) ((MouseCursor.getCursorX() * 2) - Game.getInstance().getResolution().x);
        position.y = (float) -((MouseCursor.getCursorY() * 2) - Game.getInstance().getResolution().y);

        super.update();
    }

    public float getBlowRange() {
        return blowRange;
    }
}
