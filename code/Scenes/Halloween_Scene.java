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
public class Halloween_Scene extends Scene{
    public Halloween_Scene(MainGame game, World world){
        this(game);
        EntityFactory factory = new EntityFactory(game.getManager());
        factory.setFloor(texture_floor);
        factory.setSpike(texture_spike);

        music = Gdx.audio.newMusic(Gdx.files.internal("scary_noise.ogg"));
        this.GetFloor("Mapas/halloween.txt", factory, world);
        this.GetSpike("Mapas/halloween.txt", factory, world);
        this.GetFire("Mapas/halloween.txt", factory, world);
    }

    public Halloween_Scene(MainGame game){
        num_capas = 4;
        fondo = new Texture[num_capas];
        k = new float[num_capas];
        String hallo = "Halloween/";
        this.fondo[2] = game.getManager().get(hallo+"halloween2.png", Texture.class);
        this.fondo[1] = game.getManager().get(hallo+"arboles_halloween.png", Texture.class);
        this.fondo[0] = game.getManager().get(hallo+"cementerio1.png", Texture.class);
        this.fondo[3] = game.getManager().get(hallo+"cielo_halloween.jpg", Texture.class);
        this.texture_floor = game.getManager().get(hallo+"halloween_floor.png", Texture.class);
        this.texture_spike = game.getManager().get("cono.png", Texture.class);
    }
}
