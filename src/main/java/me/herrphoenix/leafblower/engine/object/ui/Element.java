package me.herrphoenix.leafblower.engine.object.ui;

import me.herrphoenix.leafblower.engine.object.Transform;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

public class Element extends Transform {
    private ImageTexture texture;

    public Element(ImageTexture texture, Vector2f position, Vector3f rotation, Vector3f scale) {
        super(position, rotation, scale);

        this.texture = texture;
    }

    public Element(ImageTexture texture, Vector2f position, Vector3f rotation) {
        this(texture, position, rotation, new Vector3f(texture.getWidth(), texture.getHeight(), 1f));
    }

    public ImageTexture getTexture() {
        return texture;
    }

    public void setTexture(ImageTexture texture) {
        this.texture = texture;
    }
}
