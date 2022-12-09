package me.herrphoenix.leafblower.engine.font;

import me.herrphoenix.leafblower.engine.object.Mesh;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL30.*;

public class CharacterMesh extends Mesh {
    private final float[] texCoords;

    private int tbo;

    public CharacterMesh(float[] vertices, float[] texCoords) {
        super(vertices);

        this.texCoords = texCoords;
    }

    @Override
    public void load() {
        super.load();

        glBindVertexArray(getVao());

        tbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tbo);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(texCoords.length);
            buffer.put(texCoords).flip();
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        }

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void destroy() {
        glDeleteBuffers(tbo);
        super.destroy();
    }

    public int getTbo() {
        return tbo;
    }

    public float[] getTexCoords() {
        return texCoords;
    }
}
