package com.sofie.core.rendering;

import com.sofie.core.ShaderManager;
import com.sofie.core.WindowManager;
import com.sofie.core.entity.Camera;
import com.sofie.core.entity.Entity;
import com.sofie.core.entity.terrain.Terrain;
import com.sofie.core.lighting.DirectionalLight;
import com.sofie.core.lighting.SpotLight;
import com.sofie.core.lighting.PointLight;
import com.sofie.core.utils.Consts;
import com.sofie.test.Launcher;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderManager {
    private final WindowManager window;
    private EntityRenderer entityRenderer;
    private TerrainRenderer terrainRenderer;


    public RenderManager() {
        window = Launcher.getWindow();
    }

    public void init() throws Exception{
        entityRenderer = new EntityRenderer();
        terrainRenderer = new TerrainRenderer();
        entityRenderer.init();
        terrainRenderer.init();

    }

    public static void renderLights(PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight, ShaderManager shader){
        shader.setUniform("ambientLight", Consts.AMBIENT_LIGHT);
        shader.setUniform("specularPower", Consts.SPECULAR_POWER);

        int numLights = spotLights != null ? spotLights.length : 0;
        for(int i = 0; i < numLights; i++){
            shader.setUniform("spotLights", spotLights[i], i);
        }

        numLights = pointLights != null ? pointLights.length : 0;
        for(int i = 0; i < numLights; i++){
            shader.setUniform("pointLights", pointLights[i], i);
        }
        shader.setUniform("directionalLight", directionalLight);
    }

    public void render(Camera camera, DirectionalLight directionalLight,  PointLight[] pointLights, SpotLight[] spotLights){
        clear();
        if(window.isResize()){
            GL11.glViewport(0,0,window.getWidth(), window.getWidth());
            window.setResize(false);
        }

        entityRenderer.render(camera, pointLights, spotLights, directionalLight);
        terrainRenderer.render(camera, pointLights, spotLights, directionalLight);

    }

    public void processEntity(Entity entity){
        List<Entity> entityList = entityRenderer.getEntities().get(entity.getModel());
        if(entityList != null)
            entityList.add(entity);
        else{
            List<Entity> newEntityList = new ArrayList<>();
            newEntityList.add(entity);
            entityRenderer.getEntities().put(entity.getModel(), newEntityList);
        }
    }
    public void processTerrain(Terrain terrain){
        terrainRenderer.getTerrain().add(terrain);
    }

    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup(){
        entityRenderer.cleanup();
        terrainRenderer.cleanup();

    }
}
