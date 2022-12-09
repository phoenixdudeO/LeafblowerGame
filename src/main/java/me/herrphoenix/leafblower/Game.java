package me.herrphoenix.leafblower;

import me.herrphoenix.leafblower.engine.OpenGLHelper;
import me.herrphoenix.leafblower.engine.font.FontData;
import me.herrphoenix.leafblower.engine.font.FontLoader;
import me.herrphoenix.leafblower.engine.font.FontRenderer;
import me.herrphoenix.leafblower.engine.font.TextString;
import me.herrphoenix.leafblower.engine.object.ui.Element;
import me.herrphoenix.leafblower.engine.object.ui.ImageTexture;
import me.herrphoenix.leafblower.engine.render.Renderer2D;
import me.herrphoenix.leafblower.game.Leaf;
import me.herrphoenix.leafblower.game.LeafBlower;
import me.herrphoenix.leafblower.game.LeafManager;
import me.herrphoenix.leafblower.io.MouseCursor;
import me.herrphoenix.leafblower.io.TextureLoader;
import me.herrphoenix.leafblower.io.Window;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    public static final long LEAF_SPAWN_PERIOD = 1250;

    private static Game instance;

    private final List<LeafBlower> leafBlowers = new ArrayList<>();

    private Window window;
    private Vector2f resolution;
    private float deltaTime;
    private long lastFrameTime;
    private MouseCursor mouseCursor;

    private Element vignette;

    private FontData font;
    private Renderer2D renderer;
    private FontRenderer fontRenderer;

    private TextString leavesCounter;
    private long lastLeafSpawn = 0;

    private Game() {
        instance = this;

        init();
    }

    public static Game getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Game();
    }

    private void init() {
        System.out.println("[" + currentTimeString() + "] Initializing...");

        setResolution(new Vector2f(1280, 720));

        OpenGLHelper.initializeGLFW();
        window = new Window(1280, 720, "Leaf-blower", 0, 0, true);
        mouseCursor = new MouseCursor(window);

        OpenGLHelper.initializeOpenGL(window);
        renderer = new Renderer2D();
        fontRenderer = new FontRenderer();

        OpenGLHelper.printSystemInfo();

        System.out.println("[" + currentTimeString() + "] Loading textures...");
        LeafManager.loadTextures();
        ImageTexture vignetteTexture = TextureLoader.loadTexture("/textures/vignette.png", true);

        System.out.println("[" + currentTimeString() + "] Loading fonts...");
        font = FontLoader.loadFont("arial.fnt");

        System.out.println("[" + currentTimeString() + "] Starting game...");
        leafBlowers.add(new LeafBlower(new Vector2f(0, 0), 200));

        vignette = new Element(vignetteTexture, new Vector2f(), new Vector3f());

        leavesCounter = new TextString("Leaves: " + LeafManager.getCollected(), font,
                new Vector2f(-resolution.x + 40, resolution.y - 40), 32);

        startGameLoop();
    }

    private void startGameLoop() {
        System.out.println("[" + currentTimeString() + "] Game has started successfully.");
        lastLeafSpawn = System.currentTimeMillis();

        while(!window.shouldClose()) {
            runGameLoop();
        }
        close();
    }

    private void runGameLoop() {
        OpenGLHelper.clearBuffers();

        deltaTime = (System.currentTimeMillis() - lastFrameTime) / 1000f;
        lastFrameTime = System.currentTimeMillis();

        if ((System.currentTimeMillis() - lastLeafSpawn) >= LEAF_SPAWN_PERIOD) {
            for (int i = 0; i < 10; i++) {
                LeafManager.generateRandomLeaf();
            }
            lastLeafSpawn = System.currentTimeMillis();
        }

        LeafManager.checkLeaves();

        for (Leaf leaf : LeafManager.getLeaves()) {
            leaf.update();
            renderer.renderElement(leaf);
        }

        leafBlowers.get(0).update();

//        renderer.renderElement(leafBlowers.get(0));

        renderer.renderElement(vignette);

        fontRenderer.renderText(leavesCounter);
        leavesCounter.setText("Leaves: " + LeafManager.getCollected());

        window.update();
    }

    private void close() {
        System.out.println("[" + currentTimeString() + "] Closing...");

        vignette.getTexture().destroy();
        LeafManager.unloadTextures();
        font.dispose();
        fontRenderer.dispose();
        renderer.dispose();
        mouseCursor.destroy();
        window.destroy();
        System.exit(0);
    }

    public TextString getLeavesCounter() {
        return leavesCounter;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public List<LeafBlower> getLeafBlowers() {
        return leafBlowers;
    }

    public Window getWindow() {
        return window;
    }

    public Renderer2D getRenderer() {
        return renderer;
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
        OpenGLHelper.createOrthographicProjection(resolution);
    }

    public String currentTimeString() {
        Date date = new Date();
        int h = date.getHours();
        int m = date.getMinutes();
        int s = date.getSeconds();

        return formatClockComponent(h) + ":" + formatClockComponent(m) + ":" + formatClockComponent(s);
    }

    private static String formatClockComponent(int comp) {
        return comp < 10 ? "0" + comp : String.valueOf(comp);
    }
}
