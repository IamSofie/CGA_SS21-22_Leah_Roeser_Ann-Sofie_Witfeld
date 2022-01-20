#version 400 core

in vec2 fragTextureCoord;

out vec4 fragColour;


struct DirectionalLight {
    vec3 colour;
    vec3 direction;
    float intensity;
};

uniform float specularPower;
uniform DirectionalLight directionalLight;

uniform sampler2D textureSampler;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC; //8:06 episode 13

void main(){
    fragColour = texture(textureSampler, fragTextureCoord);
}

