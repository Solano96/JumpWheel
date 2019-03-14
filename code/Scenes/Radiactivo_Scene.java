package com.jumpwheel.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpwheel.MainGame;
import com.jumpwheel.MapSelect;
import com.jumpwheel.entities.EntityFactory;

/**
 * Created by FcoSolano on 08/09/2016.
 */
public class Radiactivo_Scene extends Scene{
    public Radiactivo_Scene(MainGame game, World world){
        this(game);
        EntityFactory factory = new EntityFactory(game.getManager());
        factory.setFloor(texture_floor);
        factory.setSpike(texture_spike);

        music = Gdx.audio.newMusic(Gdx.files.internal("electro_light.ogg"));
        this.GetFloor("Mapas/robot.txt", factory, world);
        this.GetSpike("Mapas/robot.txt", factory, world);
    }

    public Radiactivo_Scene(MainGame game){
        num_capas = 4;
        fondo = new Texture[num_capas];
        k = new float[num_capas];
        String radioactivo = "Radioactivo/";
        this.fondo[0] = game.getManager().get(radioactivo+"central_nuclear.png", Texture.class);
        this.fondo[1] = game.getManager().get("Halloween/arboles_halloween.png", Texture.class);
        this.fondo[2] = game.getManager().get(radioactivo+"pinos.png", Texture.class);
        this.fondo[3] = game.getManager().get(radioactivo+"cielo_radioactivo.jpg", Texture.class);
        this.texture_floor = game.getManager().get(radioactivo+"radioactive_floor.png", Texture.class);
        this.texture_spike = game.getManager().get("cono.png", Texture.class);
    }
}
