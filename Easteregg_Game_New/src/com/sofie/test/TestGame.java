package com.sofie.test;

import com.sofie.core.*;
import com.sofie.core.entity.*;
import com.sofie.core.lighting.DirectionalLight;
import com.sofie.core.lighting.SpotLight;
import com.sofie.core.lighting.PointLight;

import com.sofie.core.utils.Consts;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;


public class TestGame implements ILogic {

    public static final float CAMERA_MOVE_SPEED = 0.05f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;
    private final Camera camera;

    Vector3f cameraInc;

    private float lightAngle;
    private DirectionalLight directionalLight;
    private PointLight pointLight;
    private SpotLight spotLight;




    public TestGame() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        lightAngle = -90;
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        Model model = loader.loadOBJModel("/resources/models/fence.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/blue.png")), 1f);
        entity = new Entity(model, new Vector3f(1, 0, -1), new Vector3f(0, 0, 0), 1);



        //point light
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(0,0, -3.2f);
        Vector3f lightColour = new Vector3f(1,1,1);
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity, 0,1,1);

        //spot light
        Vector3f coneDir = new Vector3f(0,0,1);
        float cutoff = (float) Math.cos(Math.toRadians(180));
        spotLight = new SpotLight(new PointLight(lightColour, new Vector3f(0,0,1f),
                lightIntensity, 0,0,1), coneDir, cutoff);


        //directional light
        lightPosition = new Vector3f(-1,-10,0);
        lightColour = new Vector3f(1,1,1);
        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);



    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);

        if (window.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;
        if (window.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;

        if (window.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;
        if (window.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;

        if (window.isKeyPressed(GLFW.GLFW_KEY_Z))
            cameraInc.y = -1;

        if (window.isKeyPressed(GLFW.GLFW_KEY_X))
            cameraInc.y = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_O)){
            pointLight.getPosition().x += 0.1f;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_P)) {
            pointLight.getPosition().x -= 0.1f;
        }

        float lightPos = spotLight.getPointLight().getPosition().z;
        if (window.isKeyPressed(GLFW.GLFW_KEY_N)) {
            spotLight.getPointLight().getPosition().z = lightPos + 0.1f;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_M)) {
            spotLight.getPointLight().getPosition().z = lightPos - 0.1f;

        }
    }

    @Override
    public void update(MouseInput mouseInput) {
       camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);

       if(mouseInput.isRightButtonPress()){
           Vector2f rotVec = mouseInput.getDisplVec();
           camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
       }

        //entity.incRotation(0.0f, 0.25f, 0.0f);

        lightAngle += 0.5f;
       if (lightAngle > 90){
           directionalLight.setIntensity(0);
           if (lightAngle >= 360)
               lightAngle = -90;
       }else if (lightAngle <= -80 || lightAngle >= 80){
           float factor = 1 - (Math.abs(lightAngle)-80) /10.0f;
           directionalLight.setIntensity(factor);
           directionalLight.getColour().y =Math.max(factor, 0.9f);
           directionalLight.getColour().z =Math.max(factor, 0.5f);
       }else {
           directionalLight.setIntensity(1);
           directionalLight.getColour().x = 1;
           directionalLight.getColour().y = 1;
           directionalLight.getColour().z = 1;
       }
       double angRad = Math.toRadians(lightAngle);                      //cycle
       directionalLight.getDirection().x = (float) Math.sin(angRad);
       directionalLight.getDirection().y = (float) Math.cos(angRad);
    }

    @Override
    public void render() {
        renderer.render(entity, camera, directionalLight, pointLight, spotLight);
    }

    @Override
    public void cleanup() {
    renderer.cleanup();
    loader.cleanup();
    }
}
