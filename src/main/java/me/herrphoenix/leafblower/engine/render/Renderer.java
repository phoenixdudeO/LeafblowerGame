package me.herrphoenix.leafblower.engine.render;

import me.herrphoenix.leafblower.engine.object.Mesh;

import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    public void render(Mesh mesh) {
        glBindVertexArray(mesh.getVao());
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, mesh.getVertices().length / 2);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    protected void enableBlending() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    protected void disableBlending() {
        glDisable(GL_BLEND);
    }
}
