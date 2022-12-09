package me.herrphoenix.leafblower.io;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

public class Window {
    private final long id;
    private final GLFWWindowSizeCallback windowSizeCallback;

    private String title;
    private int xPos;
    private int yPos;

    public Window(int width, int height, String title, int xPos, int yPos, boolean centered) {
        this.title = title;
        this.xPos = xPos;
        this.yPos = yPos;

        id = glfwCreateWindow(width, height, title, 0, 0);
        if (id == 0) {
            System.err.println("Couldn't initialize window. Terminating...");
            System.exit(-1);
        }

        if (centered) {
            GLFWVidMode monitor = glfwGetVideoMode(glfwGetPrimaryMonitor());
            this.xPos = (monitor.width() - width) / 2;
            this.yPos = (monitor.height() - height) / 2;
        }

        glfwSetWindowPos(id, this.xPos, this.yPos);

        glfwShowWindow(id);
        glfwMakeContextCurrent(id);
        glfwSwapInterval(1);

        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long id, int newWidth, int newHeight) {
                glViewport(0, 0, newWidth, newHeight);
//                OpenGLHelper.createOrthographicProjection(newWidth, newHeight);

//                Game.getInstance().getLeavesCounter().setPosition(new Vector2f(-(newWidth - 40), newHeight - 41));
            }
        };

        glfwSetWindowSizeCallback(id, windowSizeCallback);
    }

    public void update() {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    public void destroy() {
        windowSizeCallback.free();
        glfwDestroyWindow(id);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(id);
    }

    public long getId() {
        return id;
    }

    public int getWidth() {
        int[] width = new int[1];
        glfwGetWindowSize(id, width, null);
        return width[0];
    }

    public int getHeight() {
        int[] height = new int[1];
        glfwGetWindowSize(id, null, height);
        return height[0];
    }

    public void setSize(int width, int height) {
        glfwSetWindowSize(id, width, height);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(id, this.title);
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        glfwSetWindowPos(id, this.xPos, this.yPos);
    }
}
