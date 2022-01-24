package com.sofie.core.rendering;

import com.sofie.core.entity.Camera;
import com.sofie.core.entity.Model;
import com.sofie.core.lighting.DirectionalLight;
import com.sofie.core.lighting.PointLight;
import com.sofie.core.lighting.SpotLight;

public interface IRendering<T> {

    public void init() throws Exception;

    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight);

    abstract void bind (Model model);

    public void unbind();

    public void prepare(T t, Camera camera);

    public void cleanup();

}
