package me.herrphoenix.leafblower.engine.font;

public class CharacterData {
    private final int id;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final int xOffset;
    private final int yOffset;
    private final int xAdvance;

    private CharacterMesh mesh;

    public CharacterData(int id, int xPos, int yPos, int width, int height, int xOffset, int yOffset, int xAdvance) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
    }

    public CharacterMesh getMesh() {
        return mesh;
    }

    public void setMesh(CharacterMesh mesh) {
        this.mesh = mesh;
        this.mesh.load();
    }

    public int getId() {
        return id;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public int getXAdvance() {
        return xAdvance;
    }
}
