package me.herrphoenix.leafblower.engine.font;

import me.herrphoenix.leafblower.engine.object.ui.Element;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class TextString {
    private final Map<Integer, Element> elements = new HashMap<>();

    private String text;
    private FontData font;
    private Vector2f position;
    private float size;

    public TextString(String text, FontData font, Vector2f position, float size) {
        this.text = text;
        this.font = font;
        this.position = position;
        this.size = size;

        createMesh();
    }

    public void createMesh() {
        int cursor = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            CharacterData character = font.getCharacter(c);

            float atlasWidth = font.getAtlasTexture().getWidth();
            float atlasHeight = font.getAtlasTexture().getHeight();

            float sizeRatio = size / (float) font.getBaseSize();

            float charHeight = character.getHeight();
            float charWidth = character.getWidth();

            float xSize = charWidth * sizeRatio;
            float ySize = charHeight * sizeRatio;

            float xPos = character.getXPos();
            float yPos = character.getYPos();

            Element elem = elements.get(i);
            if (elem == null) {
                elem = new Element(font.getAtlasTexture(),
                        new Vector2f(position.x + ((cursor + character.getXOffset()) * sizeRatio),
                                position.y - ((character.getYOffset()) * sizeRatio)), new Vector3f(),
                        new Vector3f(xSize, ySize, 1));
            } else {
                elem.setTexture(font.getAtlasTexture());
                elem.getPosition().x = position.x + ((cursor + character.getXOffset()) * sizeRatio);
                elem.getPosition().y = position.y - ((character.getYOffset()) * sizeRatio);
                elem.setScale(new Vector3f(xSize, ySize, 1));
            }

            elements.put(i, elem);

            cursor += character.getXAdvance();

            if (character.getMesh() == null) {
                float[] texCoords = new float[] {
                        xPos / atlasWidth, ((yPos + charHeight) / atlasHeight),
                        xPos / atlasWidth, (yPos / atlasHeight),
                        (xPos + charWidth) / atlasWidth, (yPos / atlasHeight),
                        (xPos + charWidth) / atlasWidth, (yPos / atlasHeight),
                        (xPos + charWidth) / atlasWidth, ((yPos + charHeight) / atlasHeight),
                        xPos / atlasWidth, ((yPos + charHeight) / atlasHeight)
                };
                character.setMesh(new CharacterMesh(FontRenderer.HALF_QUAD, texCoords));
            }
        }
    }

    public Map<Integer, Element> getElements() {
        return elements;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        createMesh();
    }

    public FontData getFont() {
        return font;
    }

    public void setFont(FontData font) {
        this.font = font;
        createMesh();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
        createMesh();
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
