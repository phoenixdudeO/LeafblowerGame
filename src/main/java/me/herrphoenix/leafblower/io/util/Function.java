package me.herrphoenix.leafblower.io.util;

public class Function {
    public static float lerp(float a, float b, float t) {
        return a * (1.0f - t) + (b * t);
    }
}
