package me.herrphoenix.leafblower.engine.font;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.engine.OpenGLHelper;
import me.herrphoenix.leafblower.engine.object.ui.Element;
import me.herrphoenix.leafblower.engine.render.Renderer2D;
import me.herrphoenix.leafblower.engine.shader.Shader;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import static org.lwjgl.opengl.GL30.*;

public class FontRenderer extends Renderer2D {
    public static final float[] HALF_QUAD = new float[] {
            0f, -1f, 0f, 0f, 1f, 0f, 1f, 0f, 1f, -1f, 0f, -1f
    };

    public FontRenderer() {
        shader = new Shader("/shaders/textVert.glsl", "/shaders/frag.glsl");
    }

    public void renderText(TextString text) {
        for (int i = 0; i < text.getText().length(); i++) {
            char c = text.getText().charAt(i);

            Element element = text.getElements().get(i);
            CharacterMesh mesh = text.getFont().getCharacter(c).getMesh();

            renderCharacter(element, mesh);
        }
    }

    @Deprecated
    public void renderText(String text, FontData font, Vector2f position, float size) {
        int cursor = 0;

        for (char c : text.toCharArray()) {
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

            float[] texCoords = new float[] {
                xPos / atlasWidth, ((yPos + charHeight) / atlasHeight),
                xPos / atlasWidth, (yPos / atlasHeight),
                (xPos + charWidth) / atlasWidth, (yPos / atlasHeight),
                (xPos + charWidth) / atlasWidth, (yPos / atlasHeight),
                (xPos + charWidth) / atlasWidth, ((yPos + charHeight) / atlasHeight),
                xPos / atlasWidth, ((yPos + charHeight) / atlasHeight)
            };

            Element element = new Element(font.getAtlasTexture(),
                    new Vector2f(position.x + ((cursor + character.getXOffset()) * sizeRatio),
                            position.y - ((character.getYOffset()) * sizeRatio)), new Vector3f(),
                    new Vector3f(xSize, ySize, 1f));

            cursor += character.getXAdvance();

            CharacterMesh charMesh = new CharacterMesh(HALF_QUAD, texCoords);
            charMesh.load();

            renderCharacter(element, charMesh);
        }
    }

    protected void renderCharacter(Element element, CharacterMesh characterMesh) {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, element.getTexture().getId());

        shader.bind();
        shader.loadUniform4fm("transform", element.getMatrix());
        shader.loadUniform4fm("projection", OpenGLHelper.getOrthographicProjection(Game.getInstance().getResolution()));

        glBindVertexArray(characterMesh.getVao());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(GL_TRIANGLES, 0, characterMesh.getVertices().length / 2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        shader.unbind();

        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
