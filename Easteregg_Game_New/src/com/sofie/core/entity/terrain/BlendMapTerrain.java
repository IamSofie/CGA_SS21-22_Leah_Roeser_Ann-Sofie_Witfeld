package com.sofie.core.entity.terrain;

public class BlendMapTerrain {

    TerrainTexture background, greenTexture, redTexture, blueTexture;

    public BlendMapTerrain(TerrainTexture background, TerrainTexture greenTexture, TerrainTexture redTexture, TerrainTexture blueTexture) {
        this.background = background;
        this.greenTexture = greenTexture;
        this.redTexture = redTexture;
        this.blueTexture = blueTexture;
    }

    public TerrainTexture getBackground() {
        return background;
    }

    public TerrainTexture getGreenTexture() {
        return greenTexture;
    }

    public TerrainTexture getRedTexture() {
        return redTexture;
    }

    public TerrainTexture getBlueTexture() {
        return blueTexture;
    }
}
