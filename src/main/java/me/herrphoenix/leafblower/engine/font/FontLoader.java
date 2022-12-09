package me.herrphoenix.leafblower.engine.font;

import me.herrphoenix.leafblower.engine.object.ui.ImageTexture;
import me.herrphoenix.leafblower.io.TextureLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FontLoader {
    public static final String FONT_DIR = "/fonts/";

    public static FontData loadFont(String fontFile) {
        InputStream inputStream = FontLoader.class.getResourceAsStream(FONT_DIR + fontFile);

        Scanner reader = new Scanner(inputStream);

        ImageTexture atlasTexture = null;
        List<CharacterData> characters = new ArrayList<>();
        int baseSize = 0;

        while(reader.hasNextLine()) {
            String line = reader.nextLine();

            if (line.startsWith("info")) {
                String[] data = line.split(" ");

                for (String str : data) {
                    if (str.contains("size")) {
                        baseSize = Integer.parseInt(str.split("=")[1]);
                    }
                }
            }
            if (line.startsWith("page id=0 file=")) {
                String texFile = line.split("=")[2].replace("\"", "");
                atlasTexture = TextureLoader.loadTexture(FONT_DIR + texFile, true);
            }
            if (line.startsWith("char id=")) {
                String values = line.replace("char ", "");
                String[] charData = values.split(" ");

                int id = 0;
                int x = 0;
                int y = 0;
                int w = 0;
                int h = 0;
                int xOffset = 0;
                int yOffset = 0;
                int xAdvance = 0;

                for (String data : charData) {
                    if (!data.contains("=")) {
                        continue;
                    }

                    String field = data.split("=")[0];
                    int value = Integer.parseInt(data.split("=")[1]);

                    switch(field) {
                        case "id":
                            id = value;
                        case "x":
                            x = value;
                        case "y":
                            y = value;
                        case "width":
                            w = value;
                        case "height":
                            h = value;
                        case "xoffset":
                            xOffset = value;
                        case "yoffset":
                            yOffset = value;
                        case "xadvance":
                            xAdvance = value;
                    }
                }

                characters.add(new CharacterData(id, x, y, w, h, xOffset, yOffset, xAdvance));
            }
        }

        return new FontData(atlasTexture, characters, baseSize);
    }
}
