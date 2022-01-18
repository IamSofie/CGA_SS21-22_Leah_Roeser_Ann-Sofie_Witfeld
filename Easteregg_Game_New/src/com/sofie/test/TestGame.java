package com.sofie.test;

import com.sofie.core.ILogic;
import com.sofie.core.ObjectLoader;
import com.sofie.core.RenderManager;
import com.sofie.core.WindowManager;
import com.sofie.core.entity.Camera;
import com.sofie.core.entity.Entity;
import com.sofie.core.entity.Model;
import com.sofie.core.entity.Texture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {

    public static final float CAMERA_MOVE_SPEED = 0.05f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        Model model = loader.loadOBJModel("/resources/models/neptune.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/blue.png")));
        entity = new Entity(model, new Vector3f(1, 0, -250), new Vector3f(0, 0, 0), 1);
    }

    @Override
    public void input() {
    cameraInc.set(0,0,0);

        if(window.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;
         if(window.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_Z))
            cameraInc.y = -1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_X))
            cameraInc.y = 1;
    }

    @Override
    public void update() {
       camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);

       entity.incRotation(0.0f, 0.5f, 0.0f);

    }

    @Override
    public void render() {
        if (window.isResize()){
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        renderer.render(entity, camera);
    }

    @Override
    public void cleanup() {
    renderer.cleanup();
    loader.cleanup();
    }
}
