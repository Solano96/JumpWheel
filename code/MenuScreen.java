package com.jumpwheel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jumpwheel.Buttons.Button;
import com.jumpwheel.Buttons.ButtonExit;
import com.jumpwheel.Buttons.ButtonMenu;

import static com.jumpwheel.Constants.*;

/**
 * This is the screen that you see when you enter the game. It has a button for playing the game.
 * When you press this button, you go to the game screen so that you can start to play. This
 * screen was done by copying the code from GameOverScreen. All the cool comments have been
 * copy-pasted.
 */
public class MenuScreen extends BaseScreen {

    private Stage stage;

    private Button exit, play;

    private Texture fondo, titulo;

    private Image f;

    private RPantalla rpantalla;

    public MenuScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        fondo = game.getManager().get("inicio.jpg", Texture.class);
        titulo = new Texture("titulo.png");

        int ancho = (int)(WIDTH /8f);
        int alto = (int)(HEIGHT /4f);

        play = new ButtonMenu(0, alto/4+alto+alto/12, (int)(ancho*4f), alto, game);
        exit = new ButtonExit(0, alto/4, (int)(ancho*4f), alto, game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        play.update();
        exit.update();

        stage.getBatch().begin();
        stage.getBatch().draw(fondo, 0, 0, WIDTH, HEIGHT);
        //stage.getBatch().draw(titulo, 0, Gdx.graphics.getHeight()-204, 378, 204);
        play.draw(stage.getBatch());
        exit.draw(stage.getBatch());
        stage.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //cuando se cambie de tamaño se redimensionara el juego
        Gdx.gl.glViewport( 0, 0, width, height);
    }
}