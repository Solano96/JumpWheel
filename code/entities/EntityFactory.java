package com.jumpwheel.entities;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * This class creates entities using Factory Methods.
 */
public class EntityFactory {

    private AssetManager manager;
    private Texture spike, floor;

    public EntityFactory(AssetManager manager) {
        this.manager = manager;
    }

    public PlayerEntity createPlayer(World world, Vector2 position) {
        Texture playerTexture = manager.get("wheel.png");
        return new PlayerEntity(world, playerTexture, position);
    }

    public FloorEntity createFloor(World world, int x, int width, int y) {
        Texture floorTexture = floor;
        return new FloorEntity(world, floorTexture, x, width, y);
    }

    public SpikeEntity createSpikes(World world, float x, float y) {
        Texture spikeTexture = spike;
        return new SpikeEntity(world, spikeTexture, x, y);
    }

    public FireEntity createFire(World world, float x, float y) {
        Texture fuego[] = new Texture[4];
        fuego[0] = manager.get("Fuego/1.png");
        fuego[1] = manager.get("Fuego/2.png");
        fuego[2] = manager.get("Fuego/3.png");
        fuego[3] = manager.get("Fuego/4.png");
        return new FireEntity(world, fuego, x, y);
    }

    public void setFloor(Texture floor){
        this.floor = floor;
    }

    public void setSpike(Texture spike){
        this.spike = spike;
    }

}
