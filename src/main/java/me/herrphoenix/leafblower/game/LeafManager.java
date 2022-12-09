package me.herrphoenix.leafblower.game;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.engine.object.ui.ImageTexture;
import me.herrphoenix.leafblower.io.TextureLoader;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeafManager {
    private static final List<Leaf> leaves = new ArrayList<>();
    private static final List<Leaf> toRemove = new ArrayList<>();

    private static List<ImageTexture> leafTextures;
    private static int collected = 0;
    private static Random random;

    public static void loadTextures() {
        leafTextures = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            leafTextures.add(TextureLoader.loadTexture("/textures/leaf_" + i + ".png"));
        }
    }

    public static void unloadTextures() {
        for (ImageTexture tex : leafTextures) {
            tex.destroy();
        }
    }

    public static ImageTexture getRandomLeafTexture() {
        if (random == null) {
            random = new Random();
        }

        int i = random.nextInt(leafTextures.size());
        return leafTextures.get(i);
    }

    public static void generateRandomLeaf() {
        if (leaves.size() > 100) {
            return;
        }

        if (random == null) {
            random = new Random();
        }

        int width = (int) Game.getInstance().getResolution().x;
        int height = (int) Game.getInstance().getResolution().y;

        float x = random.nextInt(width * 2) - width;
        float y = random.nextInt(height * 2) - height;

        float zRot = random.nextInt(360);

        registerLeaf(new Leaf(new Vector2f(x, y), new Vector3f(0, 0, zRot)));
    }

    public static void checkLeaves() {
        toRemove.clear();

        for (Leaf leaf : leaves) {
            if (checkLeaf(leaf)) {
                toRemove.add(leaf);
            }
        }

        unregisterLeaves(toRemove);
    }

    public static int getCollected() {
        return collected;
    }

    public static boolean checkLeaf(Leaf leaf) {
        return Math.abs(leaf.getPosition().x) > Game.getInstance().getResolution().x ||
                Math.abs(leaf.getPosition().y) > Game.getInstance().getResolution().y;
    }

    public static void registerLeaf(Leaf leaf) {
        leaves.add(leaf);
    }

    public static void unregisterLeaf(Leaf leaf) {
        leaves.remove(leaf);
        collected++;
        leaf = null;
    }

    public static void unregisterLeaves(List<Leaf> toRemove) {
        leaves.removeAll(toRemove);
        collected += toRemove.size();

        for (Leaf leaf : toRemove) {
            leaf = null;
        }

        toRemove.clear();
    }


    public static List<Leaf> getLeaves() {
        return leaves;
    }
}
