package com.jumpwheel.Buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpwheel.GameScreen;

import static com.jumpwheel.Constants.GAME_HEIGHT;
import static com.jumpwheel.Constants.HEIGHT;

/**
 * Created by FcoSolano on 11/09/2016.
 */
public class ButtonPause extends Button{
    private GameScreen screen;
    private Texture pause, resume;
    private Image imagen;
    private int _x;

    public ButtonPause(int x, int y, int width, int height, GameScreen screen) {
        super(x, y, width, height);
        _x = x;
        this.screen = screen;
        boton_sin_pulsar = new Texture("pause.png");
        boton_pulsado = new Texture("resume.png");
    }

    @Override
    protected void funcionamiento() {
        if(screen.state() == GameScreen.State.RUNNING){
            screen.pause();
        }
        else{
            screen.resume();
        }
    }

    @Override
    public void draw(Batch batch) {
        Color color = batch.getColor();
        batch.setColor(1,1,1,1f);
        if(screen.state() == GameScreen.State.RUNNING)
            batch.draw(boton_sin_pulsar, bordes.x, bordes.y*GAME_HEIGHT/HEIGHT, bordes.width, bordes.height);
        else
            batch.draw(boton_pulsado, bordes.x, bordes.y*GAME_HEIGHT/HEIGHT, bordes.width, bordes.height);
        batch.setColor(color);
    }

    @Override
    public void update() {
        super.update();
        bordes = new Rectangle(screen.cero_relativo()+_x, bordes.getY(), bordes.getWidth(), bordes.getHeight());
    }
}
