#version 400 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;

out vec4 fragColour;

struct DirectionalLight {
    vec3 colour;
    vec3 direction;
    float intensity;
};


struct Material{
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflect;
    };


uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform Material material;
uniform float specularPower;
uniform DirectionalLight directionalLight;

vec4 ambientC;
//8:06 episode 13

void main(){

    if(material.hasTexture == 1) {
        ambientC = texture(textureSampler, fragTextureCoord);
        }else{
        ambientC = material.ambient + material.specular + material.diffuse + material.reflect;
        }
    }

    fragColour = ambientC * vec4(ambientLight, 1);
}

