package com.jumpwheel.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpwheel.MainGame;
import com.jumpwheel.MapSelect;
import com.jumpwheel.entities.EntityFactory;

/**
 * Created by FcoSolano on 07/09/2016.
 */
public class Mutantes_Scene extends Scene{
    public Mutantes_Scene(MainGame game, World world){
        this(game);
        EntityFactory factory = new EntityFactory(game.getManager());
        factory.setFloor(texture_floor);
        factory.setSpike(texture_spike);

        music = Gdx.audio.newMusic(Gdx.files.internal("electro_light.ogg"));
        this.GetFloor("Mapas/robot.txt", factory, world);
        this.GetSpike("Mapas/robot.txt", factory, world);
        change_blue = true;
    }

    public Mutantes_Scene(MainGame game){
        num_capas = 1;
        fondo = new Texture[num_capas];
        k = new float[num_capas];
        String mutantes = "Mutantes/";
        this.fondo[0] = game.getManager().get(mutantes+"ventanas.jpg", Texture.class);
        this.texture_floor = game.getManager().get(mutantes+"mutants_floor.png", Texture.class);
        this.texture_spike = game.getManager().get("cono.png", Texture.class);
    }
}
