package com.jumpwheel.Buttons;


import com.jumpwheel.Constants;
import com.jumpwheel.GameScreen;
import com.jumpwheel.MainGame;

/**
 * Created by MiToshiba on 24/08/2016.
 */
public class ButtonRetry extends Button {

    private MainGame game;
    private GameScreen screen;

    public ButtonRetry(int x, int y, int width, int height, MainGame game, GameScreen screen) {
        super(x, y, width, height);
        this.game = game;
        this.screen = screen;
        cadena = Constants.REINICIAR;
    }

    @Override
    protected void funcionamiento() {
        screen.resume();
        game.setScreen(game.gameScreen);
    }
}