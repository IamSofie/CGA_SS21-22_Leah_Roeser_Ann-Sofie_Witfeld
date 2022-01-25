package com.sofie.core.utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Consts {
    public static final float FOV = (float) Math.toRadians(60f);
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000f;
    public static final float SPECULAR_POWER = 10f;

    public static final int MAX_SPOT_LIGHTS = 5;
    public static final int MAX_POINT_LIGHTS = 5;


    public static final String TITLE = "Find some Easter Eggs";

    public static final float MOUSE_SENSITIVITY = 0.2f;
    public static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f,1.0f, 1.0f, 1.0f);
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(0.3f, 0.3f, 0.3f);


}
