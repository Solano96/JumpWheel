package com.jumpwheel;

import com.badlogic.gdx.Gdx;

/**
 * Created by FcoSolano on 21/08/2016.
 */
public class Constants {
    public static final int W = (int)(640);
    public static final int H = (int)(480);
    public static final int WIDTH = W;
    public static final int HEIGHT = H*W/Gdx.graphics.getWidth();
    public static final int GAME_HEIGHT = Gdx.graphics.getHeight()*W/Gdx.graphics.getWidth();
    public static final int GAME_WIDTH = W;
    public static final float PIXELS_IN_METER = W/17f;
    public static final int IMPULSE_JUMP = 80;
    public static final float PLAYER_SPEED = 8.57f;
    public static final float PLAYER_ACELERATE = 12.87f;
    public static final float VELOCITY_TRICK = 50f;

    public static final float DIV_PULSADO = 4f;
    public static final float DIV_SIN_PULSAR = 3.5f;

    public static final String CONTINUAR = "continuar";
    public static final String SALIR = "salir";
    public static final String MENU = "menu";
    public static final String JUGAR = "jugar";
    public static final String REINICIAR = "reiniciar";
}

