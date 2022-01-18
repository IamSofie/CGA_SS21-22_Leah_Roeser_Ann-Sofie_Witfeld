package com.sofie.core.entity;

<<<<<<< HEAD
import org.joml.Vector3f;

<<<<<<< HEAD
public class Entity {
    /*public Entity() {
        System.out.println("Hallo ich bin genervt");
    }*/

    private Model model;
    private Vector3f pos, rotation;
    private final float scale;

    public Entity(Model model, Vector3f pos, Vector3f rotation, float scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void incPos(float x, float y, float z){
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }

    public void setPos(float x, float y, float z){
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    public void incRotation(float x, float y, float z){
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public void setRotation(float x, float y, float z){
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }
=======
    public Entity(){
        System.out.println("HAllo Test");
    }
    
>>>>>>> a72a6d0a6d30108e9fbc046286ebcb9aa3ec5e9c
=======
public class Entity {
    public Entity() {

        System.out.println("Hallo"); 
    }
>>>>>>> temporary
}
