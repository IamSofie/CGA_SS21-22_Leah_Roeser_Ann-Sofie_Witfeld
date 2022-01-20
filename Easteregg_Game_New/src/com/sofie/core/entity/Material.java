package com.sofie.core.entity;

import com.sofie.core.utils.Consts;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class Material{

    private Vector4f ambientColor, diffuseColor, specularColor;
    private float reflect;
    private Texture texture;

    public Material(){
        this.ambientColor = Consts.DEFAULT_COLOUR;
        this.diffuseColor = Consts.DEFAULT_COLOUR;
        this.specularColor = Consts.DEFAULT_COLOUR;
        this.texture = null;
        this.reflect = 0;
    }
    public Material(Vector4f color, float reflect){
        this(color,color,color,reflect, null);
    }

    public Material(Vector4f colour, float reflect,Texture texture){
        this(colour, colour, colour, reflect, texture);
    }

    public Material(Texture texture){
        this(Consts.DEFAULT_COLOUR, Consts.DEFAULT_COLOUR, Consts.DEFAULT_COLOUR, 0, texture);
    }

    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, float reflect, Texture texture){
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflect = reflect;
        this.texture = texture;
    }
    public Vector4f getAmbientColor(){
        return ambientColor;
    }

    public void setAmbientColor(Vector4f ambientColor){
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuseColor(){
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor){
        this.diffuseColor = diffuseColor;
    }

    public Vector4f getSpecularColor(){
        return specularColor;
    }

    public void setSpecularColor(Vector4f specularColor){
        this.specularColor = specularColor;
    }

    public float getReflect(){
        return reflect;
    }

    public void setReflect(float reflect){
        this.reflect = reflect;
    }

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public boolean hasTexture(){
        return texture != null;
    }
}
