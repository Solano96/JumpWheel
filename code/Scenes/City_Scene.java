package com.jumpwheel.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.BufferUtils;
import com.jumpwheel.MainGame;
import com.jumpwheel.MapSelect;
import com.jumpwheel.entities.EntityFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by FcoSolano on 11/09/2016.
 */
public class City_Scene extends Scene{
    public City_Scene(MainGame game, World world){
        this(game);
        EntityFactory factory = new EntityFactory(game.getManager());
        factory.setFloor(texture_floor);
        factory.setSpike(texture_spike);
        music = Gdx.audio.newMusic(Gdx.files.internal("electro_light.ogg"));
        this.GetFloor("Mapas/robot.txt", factory, world);
        this.GetSpike("Mapas/robot.txt", factory, world);
    }

    public City_Scene(MainGame game){
        num_capas = 4;
        fondo = new Texture[num_capas];
        k = new float[num_capas];
        String ciudad = "Ciudad/";
        this.fondo[0] = game.getManager().get(ciudad+"ciudad1.png", Texture.class);
        this.fondo[1] = game.getManager().get(ciudad+"ciudad2.png", Texture.class);
        this.fondo[2] = game.getManager().get(ciudad+"ciudad1.png", Texture.class);
        this.fondo[3] = game.getManager().get(ciudad+"cielo_azul.jpg", Texture.class);
        this.texture_floor = game.getManager().get(ciudad+"city_floor.png", Texture.class);
        this.texture_spike = game.getManager().get("buda.png", Texture.class);
    }
}
