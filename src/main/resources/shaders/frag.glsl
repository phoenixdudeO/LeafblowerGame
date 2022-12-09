#version 330 core

in vec2 texCoords;

out vec4 outColor;

uniform sampler2D tex;

void main() {
    vec4 texColor = texture(tex, texCoords);

    if (texColor.a < 0.01) {
        discard;
    }

    outColor = texColor;
}
