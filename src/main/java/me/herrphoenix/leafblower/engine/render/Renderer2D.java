package me.herrphoenix.leafblower.engine.render;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.engine.OpenGLHelper;
import me.herrphoenix.leafblower.engine.object.Mesh;
import me.herrphoenix.leafblower.engine.object.ui.Element;
import me.herrphoenix.leafblower.engine.shader.Shader;
import me.herrphoenix.leafblower.io.Window;

import static org.lwjgl.opengl.GL30.*;

public class Renderer2D extends Renderer {
    private static final float[] QUAD_VERTICES = new float[] {
            -1, -1,
            -1, 1,
            1, 1,
            1, 1,
            1, -1,
            -1, -1
    };

    protected final Mesh quadMesh;
    protected Shader shader;

    public Renderer2D() {
        shader = new Shader("/shaders/vert.glsl", "/shaders/frag.glsl");

        quadMesh = new Mesh(QUAD_VERTICES);
        quadMesh.load();

        enableBlending();
    }

    public void renderElement(Element element) {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, element.getTexture().getId());

        shader.bind();
        shader.loadUniform4fm("transform", element.getMatrix());
        shader.loadUniform4fm("projection", OpenGLHelper.getOrthographicProjection(Game.getInstance().getResolution()));

        render(quadMesh);

        shader.unbind();

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void dispose() {
        shader.destroy();
        quadMesh.destroy();
    }
}
