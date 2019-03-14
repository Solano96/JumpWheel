package com.jumpwheel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jumpwheel.Buttons.Button;
import com.jumpwheel.Buttons.ButtonPause;
import com.jumpwheel.Scenes.China_Scene;
import com.jumpwheel.Scenes.Radiactivo_Scene;
import com.jumpwheel.Scenes.Halloween_Scene;
import com.jumpwheel.Scenes.Mutantes_Scene;
import com.jumpwheel.Scenes.City_Scene;
import com.jumpwheel.Scenes.Scene;
import com.jumpwheel.entities.EntityFactory;
import com.jumpwheel.entities.FireEntity;
import com.jumpwheel.entities.FloorEntity;
import com.jumpwheel.entities.PlayerEntity;
import com.jumpwheel.entities.SpikeEntity;

/**
 * Created by FcoSolano on 21/08/2016.
 */

import java.awt.Paint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jumpwheel.Constants.*;
import static com.jumpwheel.Constants.PIXELS_IN_METER;


/**
 * This is the main screen for the game. All the fun happen here.
 */
public class GameScreen extends BaseScreen {

    public enum State{
        RUNNING, PAUSE
    }

    private State state;

    private Stage stage;
    private World world;

    // Entities
    private PlayerEntity player;
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    private List<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();
    private List<FireEntity> fireList = new ArrayList<FireEntity>();

    private Vector3 position;
    private Texture fondo[];
    private  TextureRegion rojo;
    private int num_capas;
    private float pos_fondo = 0;
    private Scene scene;
    private Button pause;
    private int ancho = (int)(WIDTH /8);
    private int alto = (int)(HEIGHT /4);
    private PauseScreen pauseScreen;
    private ContadorMetros contador;
    private Music music;
    private int posicion = 0;

    private Pair<Integer, Integer> posAdversario = new Pair<Integer, Integer>(0,0);

    public GameScreen(MainGame game) {
        super(game);
        contador = new ContadorMetros(game);
    }

    /**
     * This method will be executed when this screen is about to be rendered.
     * Here, I use this method to set up the initial position for the stage.
     */
    @Override
    public void show() {
        stage = new Stage(new FitViewport(GAME_WIDTH, GAME_HEIGHT));
        position = new Vector3(stage.getCamera().position);

        // Creamos el boton de pause y la pantalla de pause
        int tam_pause = (int)(ancho/1.7f);
        pause = new ButtonPause(WIDTH -(int)(tam_pause*1.4f), HEIGHT - (int)(tam_pause*1.4f*HEIGHT/GAME_HEIGHT), tam_pause, tam_pause, this);
        pauseScreen = new PauseScreen(game, this);

        // Creamos el mundo del juego (Gravedad 10 m/s*s)
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new GameContactListener());

        // Inicializamos el juego a running
        state = State.RUNNING;

        EntityFactory factory = new EntityFactory(game.getManager());

        player = factory.createPlayer(world, new Vector2(4f, 1.5f));

        // Inicializamos el escenario al escenario seleccionado
        try {
            scene = SceneSelected();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.addActor(scene);
        floorList = scene.suelo();
        spikeList = scene.spike();
        fireList = scene.fire();

        for (FloorEntity floor : floorList)
            stage.addActor(floor);
        for (SpikeEntity spike : spikeList)
            stage.addActor(spike);
        for (FireEntity fire : fireList)
            stage.addActor(fire);

        stage.addActor(player);

        stage.getCamera().position.set(position);
        stage.getCamera().update();

        for(int i = 0; i < num_capas; i++)
            fondo[i] = scene.fondo(i);

        music = scene.getMusic();
        music.play();

        rojo = new TextureRegion(game.getManager().get("wheel.png", Texture.class));
    }


    @Override
    public void hide() {
        stage.clear();
        player.detach();

        for (FloorEntity floor : floorList)
            floor.detach();

        for (SpikeEntity spike : spikeList)
            spike.detach();


        for (FireEntity fire : fireList)
            fire.detach();

        floorList.clear();
        spikeList.clear();
        fireList.clear();
        scene.clear();
        music.stop();
        music.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyJustPressed(Input.Keys.P))
            if(state == State.RUNNING)
                state = State.PAUSE;
            else
                state = State.RUNNING;

        float pause_color = 1;
        if(state == State.PAUSE) pause_color = 0.5f;

        if(state == State.RUNNING)
            update(delta);

        scene.setVariables(pause_color, cero_relativo());
        stage.draw();
        // Actualizamos el boton pause y la pantalla pause
        pause.update();

        if(state == State.PAUSE)
            pauseScreenUpdate();

        // Dibujamos el boton pause
        stage.getBatch().begin();
        pause.draw(stage.getBatch());
        stage.getBatch().end();

        if(player.isAlive()){
            pos_fondo = player.getX();

                posicion = (int) (player.getX());
                game.clienteTCP.setPosicion(posicion, (int) (player.getY()));
                posAdversario = game.clienteTCP.getPositionAdversario();

        }
        else{
            stage.addAction(Actions.sequence(Actions.delay(1),Actions.run(new Runnable() {
                @Override
                public void run() {
                    music.stop();
                    game.clienteTCP.setPosicion(posicion, (int) (player.getY()));
                    game.setScreen(game.gameScreen);
                }
            })));
        }

        stage.getBatch().begin();
        stage.getBatch().setColor(1,1,1,0.6f);
        stage.getBatch().draw(rojo, posAdversario.getFirst(), posAdversario.getSecond(), player.getWidth()/2, player.getHeight()/2, player.getWidth(), player.getHeight(), 1, 1, -1f*((posAdversario.getFirst()/PIXELS_IN_METER)*(360/3.141592f))%360);
        stage.getBatch().setColor(1,1,1,1);
        stage.getBatch().end();

        contador.setMetros((int)(player.getX()/PIXELS_IN_METER));
        contador.draw(stage.getBatch(), cero_relativo());
    }

    // Devuelve la posicion relativa del borde derecho de la pantalla respecto de la camara
    public float cero_relativo(){
        return stage.getCamera().position.x- GAME_WIDTH /2;
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        music.dispose();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.gl.glViewport( 0, 0, width, height);
    }

    public void pause(){
        state = State.PAUSE;
        music.pause();
    }

    public void resume(){
        state = State.RUNNING;
        music.play();
    }

    public State state(){
        return state;
    }

    public void update(float delta){
        stage.act();

        world.step(delta, 6, 2);

        if (player.isAlive()) {
            float speed = player.getVelocityX() * delta * PIXELS_IN_METER;
            stage.getCamera().translate(speed, 0, 0);
        }

        if(player.getY() < -5* PIXELS_IN_METER)
            player.setAlive(false);

        if(player.getY() < -30* PIXELS_IN_METER)
            game.setScreen(game.gameScreen);
    }

    private void pauseScreenUpdate(){
        pauseScreen.update(cero_relativo());
        stage.getBatch().begin();
        Color c = stage.getBatch().getColor();
        stage.getBatch().setColor(1,1,1,1);
        pauseScreen.draw(stage.getBatch());
        stage.getBatch().setColor(c);
        stage.getBatch().end();
    }

    // Devuelve el escenario actualmente seleccionado
    public Scene SceneSelected() throws IOException {
        if(MapSelect.map == MapSelect.Mapa.CHINA) return new China_Scene(game, world);
        else if(MapSelect.map == MapSelect.Mapa.HALLOWEEN) return new Halloween_Scene(game, world);
        else if(MapSelect.map == MapSelect.Mapa.MUTANTES)return  new Mutantes_Scene(game, world);
        else if(MapSelect.map == MapSelect.Mapa.RADIACTIVO)return new Radiactivo_Scene(game, world);
        else return new City_Scene(game, world);
    }


    private class GameContactListener implements ContactListener {

        private boolean areCollided(Contact contact, Object userA, Object userB) {
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataA == null || userDataB == null)
                return false;

            return (userDataA.equals(userA) && userDataB.equals(userB)) || (userDataA.equals(userB) && userDataB.equals(userA));
        }

        @Override
        public void beginContact(Contact contact) {

            if(areCollided(contact, "player", "floor"))
                player.setJumping(false);

            if (areCollided(contact, "player", "spike"))
                if (player.isAlive())
                    player.setAlive(false);

            if (areCollided(contact, "player", "fire"))
                if (player.isAlive()){
                    player.setVelocity(2, 20);
                    player.setAlive(false);
                }
        }

        @Override
        public void endContact(Contact contact) {
            if(areCollided(contact, "player", "floor"))
                player.setJumping(true);
        }

        @Override public void preSolve(Contact contact, Manifold oldManifold) { }
        @Override public void postSolve(Contact contact, ContactImpulse impulse) { }
    }
}