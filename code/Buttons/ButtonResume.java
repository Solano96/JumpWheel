package com.jumpwheel.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.jumpwheel.Constants;
import com.jumpwheel.GameScreen;
import com.jumpwheel.Letters;
import com.jumpwheel.MainGame;

/**
 * Created by FcoSolano on 12/09/2016.
 */
public class ButtonResume extends Button{
    private MainGame game;
    private GameScreen screen;

    public ButtonResume(int x, int y, int width, int height, MainGame game, GameScreen screen) {
        super(x, y, width, height);
        this.game = game;
        this.screen = screen;
        cadena = Constants.CONTINUAR;
    }

    @Override
    protected void funcionamiento() {
        screen.resume();
    }
}
