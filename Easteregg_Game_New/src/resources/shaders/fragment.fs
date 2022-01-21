#version 400 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;

out vec4 fragColour;

struct Material{
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflect;
    };

struct DirectionalLight {
        vec3 colour;
        vec3 direction;
        float intensity;
    };

    struct PointLight{
    vec3 colour;
    vec3 postion;
    float intensity;
    float constant;
    float linear;
    float exponent;
    }

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform Material material;
uniform float specularPower;
uniform DirectionalLight directionalLight;
uniform Pointlight pointLight;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColours(Material material, vec2 textCoord){
if(material.hasTexture == 1){
   ambientC = texture(textureSampler, textCoord);
   diffuseC = ambientC;
   specularC = ambientC;
}else{
    ambientC = material.ambient;
    diffuseC = material.diffuse;
    specularC = material.specular;
    }
}

vec4 calcLightColour(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal){
vec4 diffuseColour = vec4(0,0,0,0);
vec4 specColour = vec4(0,0,0,0);

//diffuse Light
float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
diffuseColour = diffuseC * vec4(light_colour, 1.0) * light_intensity * diffuseFactor;

//specular Colour
vec3 camera_direction = normalize(-position);
vec3 from_light_dir = -to_light_dir;
vec3 reflectedLight = normalize(reflect(from_light_dir, normal));
float specularFactor = max(dot(camera_direction, reflectedLight), 0.0);
specularFactor = pow(specularFactor, specularPower);
specColour = specularC * light_intensity * specularFactor * material.reflect * vec4(light_colour, 1.0);

return (diffuseColour + specColour);
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal){
vec3 light_dir = light.position - position;
vec3 to_light_dir = normalize(light_dir);
vec4 light_colour = calcLightColour(light.colour, light.intensity, position, to_light_dir, normal);

//attenuation
float distace = lenght(light_dir);
float attenuationInv = light.constant + light.linear * distance + light.exponen * distance * distance;
return light_colours / attenuationInv;
}

vec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal){

return calcLightColour(light.colour, light.intensity, position, normalize(light.direction), normal);
}

void main(){

    setupColours(material, fragTextureCoord);

    vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, fragPos, fragNormal);
    diffuseSpeclarComp += calcPointLight(pointLight, fragPos, fragNormal);

    fragColour = ambientC * vec4(ambientLight, 1)+ diffuseSpecularComp;
}

