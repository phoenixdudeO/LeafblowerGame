#version 330 core

in vec2 vertex;
in vec2 inTexCoords;

out vec2 texCoords;

uniform mat4 transform;
uniform mat4 projection;

void main() {
    gl_Position = projection * transform * vec4(vertex, 0.0, 1.0);
    texCoords = inTexCoords;
}
