package me.herrphoenix.leafblower.io;

import me.herrphoenix.leafblower.Game;
import me.herrphoenix.leafblower.engine.object.ui.ImageTexture;
import me.herrphoenix.leafblower.io.util.PNGDecoder;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL30.*;

public class TextureLoader {
    public static ImageTexture loadTexture(String path) {
        return loadTexture(path, false);
    }

    public static ImageTexture loadTexture(String path, boolean linear) {
        ImageTexture texture = null;

        try {
            System.out.println("[" + Game.getInstance().currentTimeString() + "] Loading texture at " + path +
                    "; Filter: " + (linear ? "linear" : "nearest neighbor"));
            InputStream inputStream = TextureLoader.class.getResourceAsStream(path);

            PNGDecoder decoder = new PNGDecoder(inputStream);

            ByteBuffer buffer = MemoryUtil.memAlloc(decoder.getWidth() * decoder.getHeight() * 4);
            decoder.decode(buffer, 4 * decoder.getWidth(), PNGDecoder.RGBA);
            buffer.flip();

            int id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0,
                    GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, linear ? GL_LINEAR : GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, linear ? GL_LINEAR : GL_NEAREST);

            texture = new ImageTexture(id, decoder.getWidth(), decoder.getHeight());
        } catch (IOException e) {
            System.out.println("[" + Game.getInstance().currentTimeString() + "] Could not load texture at " +
                    path);
            e.printStackTrace();
        }

        return texture;
    }
}
