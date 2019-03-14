package com.jumpwheel.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.jumpwheel.Constants;
import com.jumpwheel.MainGame;

/**
 * Created by MiToshiba on 24/08/2016.
 */
public class ButtonMenu extends Button {

    private MainGame game;

    public ButtonMenu(int x, int y, int width, int height, MainGame game) {
        super(x, y, width, height);
        this.game = game;
        cadena = Constants.MENU;
    }

    @Override
    protected void funcionamiento() {
        game.setScreen(game.mapsScreen);
    }
}