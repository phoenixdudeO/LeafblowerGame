package me.herrphoenix.leafblower.engine;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.io.Window;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

public class OpenGLHelper {
    private static Matrix4f orthographicProjection;

    public static void initializeGLFW() {
        if (!glfwInit()) {
            System.err.println("[" + Game.getInstance().currentTimeString() + "] Couldn't initialize GLFW. " +
                    "Terminating...");
            System.exit(-1);
        }

        System.out.println("[" + Game.getInstance().currentTimeString() + "] Initialized GLFW.");
    }

    public static void initializeOpenGL(Window window) {
        System.out.println("[" + Game.getInstance().currentTimeString() + "] Initializing OpenGL...");

        GL.createCapabilities();
        glViewport(0, 0, window.getWidth(), window.getHeight());
    }

    public static void printSystemInfo() {
        System.out.println("\nSystem info:");
        System.out.println("|  JDK Version: " + jdk.nashorn.internal.runtime.Version.fullVersion());
        System.out.println("|  OS and Architecture: " + System.getProperty("os.name") + ", " +
                System.getProperty("os.arch"));
        System.out.println("|  LWJGL Version: " + Version.getVersion());
        System.out.println("|  Running OpenGL " + glGetString(GL_VERSION) + "\n");
    }

    public static void clearBuffers() {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.3f, 0.82f, 0.2f, 1.0f);
    }

    public static void createOrthographicProjection(Vector2f resolution) {
        if (orthographicProjection == null) {
            orthographicProjection = new Matrix4f();
        }

        orthographicProjection.setIdentity();
        orthographicProjection.m00 = 1 / resolution.x;
        orthographicProjection.m11 = 1 / resolution.y;
        orthographicProjection.m22 = -2;
        orthographicProjection.m33 = 1;
        orthographicProjection.m32 = -1;
    }

    public static Matrix4f getOrthographicProjection(Vector2f resolution) {
        if (orthographicProjection == null) {
            createOrthographicProjection(resolution);
        }

        return orthographicProjection;
    }
}
