package me.herrphoenix.leafblower.engine.font;

import me.herrphoenix.leafblower.engine.object.ui.ImageTexture;

import java.util.List;

public class FontData {
    private final List<CharacterData> characters;
    private final ImageTexture atlasTexture;
    private final int baseSize;

    public FontData(ImageTexture atlasTexture, List<CharacterData> characters, int baseSize) {
        this.atlasTexture = atlasTexture;
        this.characters = characters;
        this.baseSize = baseSize;
    }

    public CharacterData getCharacter(int id) {
        for (CharacterData character : characters) {
            if (character.getId() == id) {
                return character;
            }
        }

        return null;
    }

    public void dispose() {
        for (CharacterData character : characters) {
            if (character.getMesh() == null) {
                continue;
            }
            character.getMesh().destroy();
        }

        atlasTexture.destroy();
    }

    public int getBaseSize() {
        return baseSize;
    }

    public List<CharacterData> getCharacters() {
        return characters;
    }

    public ImageTexture getAtlasTexture() {
        return atlasTexture;
    }
}
