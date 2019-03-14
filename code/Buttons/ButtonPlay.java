package com.jumpwheel.Buttons;

/**
 * Created by FcoSolano on 01/09/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.jumpwheel.Constants;
import com.jumpwheel.MainGame;

public class ButtonPlay extends Button {

    private MainGame game;

    public ButtonPlay(int x, int y, int width, int height, MainGame game) {
        super(x, y, width, height);
        this.game = game;
        cadena = Constants.JUGAR;
    }

    @Override
    protected void funcionamiento() {
        game.setScreen(game.gameScreen);
    }
}