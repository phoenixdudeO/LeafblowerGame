package me.herrphoenix.leafblower.engine.shader;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.io.util.FileReader;
import org.lwjgl.system.MemoryStack;
import org.lwjglx.util.vector.Matrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL30.*;

public class Shader {
    private final int program;

    public Shader(String vertPath, String fragPath) {
        System.out.println("[" + Game.getInstance().currentTimeString() + "] Loading shader:");
        System.out.println("|  Vertex: " + vertPath);
        System.out.println("|  Fragment: " + fragPath);

        String vertSource = FileReader.readFile(vertPath);
        String fragSource = FileReader.readFile(fragPath);

        int vertShader = glCreateShader(GL_VERTEX_SHADER);
        int fragShader = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertShader, vertSource);
        glShaderSource(fragShader, fragSource);

        glCompileShader(vertShader);
        if (glGetShaderi(vertShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(vertShader));
        }

        glCompileShader(fragShader);
        if (glGetShaderi(fragShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(fragShader));
        }

        program = glCreateProgram();
        glAttachShader(program, vertShader);
        glAttachShader(program, fragShader);

        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertShader);
        glDeleteShader(fragShader);
    }

    public void loadUniform4fm(String field, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.store(buffer);
            buffer.flip();
            glUniformMatrix4fv(glGetUniformLocation(program, field), false, buffer);
        }
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void destroy() {
        glDeleteProgram(program);
    }
}
