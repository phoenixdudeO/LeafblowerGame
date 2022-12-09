package me.herrphoenix.leafblower.engine.object.ui;

import me.herrphoenix.leafblower.Game;

import static org.lwjgl.opengl.GL30.*;

public class ImageTexture {
    private final int id;
    private final int width;
    private final int height;

    public ImageTexture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void destroy() {
        System.out.println("[" + Game.getInstance().currentTimeString() + "] Deleting texture " + id);
        glDeleteTextures(id);
    }
}
