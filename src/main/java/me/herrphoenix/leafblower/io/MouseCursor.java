package me.herrphoenix.leafblower.io;

import me.herrphoenix.leafblower.Game;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class MouseCursor {
    private final GLFWCursorPosCallback cursorPosCallback;

    private static double cursorX;
    private static double cursorY;

    public MouseCursor(Window window) {
        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long id, double x, double y) {
                cursorX = x * (Game.getInstance().getResolution().x / Game.getInstance().getWindow().getWidth());
                cursorY = y * (Game.getInstance().getResolution().y / Game.getInstance().getWindow().getHeight());
            }
        };

        glfwSetCursorPosCallback(window.getId(), cursorPosCallback);
    }

    public void destroy() {
        cursorPosCallback.free();
    }

    public static double getCursorX() {
        return cursorX;
    }

    public static double getCursorY() {
        return cursorY;
    }
}
