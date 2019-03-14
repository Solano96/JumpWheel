package com.jumpwheel.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.jumpwheel.Constants;
import com.jumpwheel.MainGame;

/**
 * Created by FcoSolano on 12/09/2016.
 */
public class ButtonExitScene extends  Button{
    private MainGame game;

    public ButtonExitScene(int x, int y, int width, int height, MainGame game) {
        super(x, y, width, height);
        this.game = game;
        cadena = Constants.SALIR;
    }

    @Override
    protected void funcionamiento() {
        game.setScreen(game.mapsScreen);
    }
}
