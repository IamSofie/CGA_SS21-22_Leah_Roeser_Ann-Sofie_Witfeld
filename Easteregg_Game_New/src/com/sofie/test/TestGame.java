package com.sofie.test;

import com.sofie.core.*;
import com.sofie.core.entity.*;
import com.sofie.core.entity.terrain.Terrain;
import com.sofie.core.lighting.DirectionalLight;
import com.sofie.core.lighting.SpotLight;
import com.sofie.core.lighting.PointLight;

import com.sofie.core.rendering.RenderManager;
import com.sofie.core.utils.Consts;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import java.util.Random;


public class TestGame implements ILogic {


    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;
    private SceneManager sceneManager;

    private final Camera camera;

    Vector3f cameraInc;

    public TestGame() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        sceneManager = new SceneManager(-90);

    }

    @Override
    public void init() throws Exception {
        renderer.init();

        Model model = loader.loadOBJModel("/resources/models/cubee.obj");
        model.getMaterial().setDisableCulling(true);
        model.setTexture(new Texture(loader.loadTexture("textures/grassblock.png")), 1f);

        Terrain terrain = new Terrain(new Vector3f(0, -1, -800), loader, new Material(new Texture(loader.loadTexture("textures/gras.png")), 0.1f));
        Terrain terrain2 = new Terrain(new Vector3f(-800, -1, -800), loader, new Material(new Texture(loader.loadTexture("textures/gras.png")), 0.1f));
        sceneManager.addTerrain(terrain);
        sceneManager.addTerrain(terrain2);



        Random rnd = new Random();
        for(int i = 0; i < 200; i++){
            float x = rnd.nextFloat() * 100 -50;
            float y = rnd.nextFloat() * 100 -50;
            float z = rnd.nextFloat() * -300;

            sceneManager.addEntity(new Entity(model, new Vector3f(x,y,z),
            new Vector3f(rnd.nextFloat() * 180, rnd.nextFloat() * 180, 0), 1));
        }
        sceneManager.addEntity(new Entity(model, new Vector3f(0,0, -2f), new Vector3f(0,0,0),1));


        //point light
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(-0.5f,-0.5f, -3.2f);
        Vector3f lightColour = new Vector3f(1,1,1);
        PointLight pointLight = new PointLight(lightColour, lightPosition, lightIntensity, 0,0,1);

        //spot light
        Vector3f coneDir = new Vector3f(0,-50,0);
        float cutoff = (float) Math.cos(Math.toRadians(140));
        lightIntensity = 50000f;
        SpotLight spotLight = new SpotLight(new PointLight(lightColour, new Vector3f(0,0,-3.6f),
                lightIntensity, 0,0,0.2f), coneDir, cutoff);

        SpotLight spotLight1 = new SpotLight(pointLight, coneDir, cutoff);
        spotLight.getPointLight().setPosition((new Vector3f(0.5f,0.5f, -3.6f)));

        //directional light
        lightPosition = new Vector3f(-1,-10,0);
        lightColour = new Vector3f(1,1,1);
        sceneManager.setDirectionalLight(new DirectionalLight(lightColour, lightPosition, lightIntensity));

        sceneManager.setPointLights(new PointLight[]{pointLight});
        sceneManager.setSpotLights(new SpotLight[]{spotLight, spotLight});

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

        /*if(window.isKeyPressed(GLFW.GLFW_KEY_O)){
            pointLights[0].getPosition().x += 0.1f;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_P)){
            pointLights[0].getPosition().x -= 0.1f;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_U)){
            pointLights[0].getPosition().z += 0.1f;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_I)){
            pointLights[0].getPosition().z -= 0.1f;
        }*/

        float lightPos = sceneManager.getSpotLights()[0].getPointLight().getPosition().z;
        if (window.isKeyPressed(GLFW.GLFW_KEY_N)) {
            sceneManager.getSpotLights()[0].getPointLight().getPosition().z = lightPos + 0.1f;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_M)) {
            sceneManager.getSpotLights()[0].getPointLight().getPosition().z = lightPos - 0.1f;

        }
    }

    @Override
    public void update(MouseInput mouseInput) {
       camera.movePosition(cameraInc.x * Consts.CAMERA_MOVE_SPEED, cameraInc.y * Consts.CAMERA_MOVE_SPEED, cameraInc.z * Consts.CAMERA_MOVE_SPEED);

       if(mouseInput.isRightButtonPress()){
           Vector2f rotVec = mouseInput.getDisplVec();
           camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
       }
        //entity.incRotation(0.0f, 0.25f, 0.0f);

        sceneManager.incSpotAngle(0.15f);
        if (sceneManager.getSpotAngle() > 4)
            sceneManager.setSpotInc(-1);
           else if(sceneManager.getSpotAngle() <= -4)
                sceneManager.setSpotInc(1);

           double spotAngleRad = Math.toRadians(sceneManager.getSpotAngle());
           Vector3f coneDir = sceneManager.getSpotLights()[0].getPointLight().getPosition();
           coneDir.y = (float) Math.sin(spotAngleRad);

           sceneManager.incLightAngle(1.1f);

           if(sceneManager.getLightAngle() > 90){
               sceneManager.getDirectionalLight().setIntensity(0);
               if(sceneManager.getLightAngle() >= 360)
                   sceneManager.setLightAngle(-90);
           } else if(sceneManager.getLightAngle() <= -80 || sceneManager.getLightAngle() >= 80){
               float factor = 1 - (Math.abs(sceneManager.getLightAngle())-80) /10.0f;
               sceneManager.getDirectionalLight().setIntensity(factor);
               sceneManager.getDirectionalLight().getColour().y =Math.max(factor, 0.9f);
               sceneManager.getDirectionalLight().getColour().z =Math.max(factor, 0.5f);
           }
       else {
            sceneManager.getDirectionalLight().setIntensity(2);
            sceneManager.getDirectionalLight().getColour().x = 1;
            sceneManager.getDirectionalLight().getColour().y = 1;
            sceneManager.getDirectionalLight().getColour().z = 1;
       }
       double angRad = Math.toRadians(sceneManager.getLightAngle());                      //cycle
        sceneManager.getDirectionalLight().getDirection().x = (float) Math.sin(angRad);
        sceneManager.getDirectionalLight().getDirection().y = (float) Math.cos(angRad);

       for(Entity entity : sceneManager.getEntities()){
           renderer.processEntity(entity);
       }

       for (Terrain terrain : sceneManager.getTerrains()){
           renderer.processTerrain(terrain);
       }
    }

    @Override
    public void render() {
        renderer.render(camera, sceneManager);
    }

    @Override
    public void cleanup() {
    renderer.cleanup();
    loader.cleanup();
    }
}
