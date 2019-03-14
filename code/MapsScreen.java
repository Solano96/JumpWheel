package com.jumpwheel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jumpwheel.Buttons.Button;
import com.jumpwheel.Buttons.ButtonPlay;
import com.jumpwheel.Scenes.China_Scene;
import com.jumpwheel.Scenes.Halloween_Scene;
import com.jumpwheel.Scenes.Mutantes_Scene;
import com.jumpwheel.Scenes.Radiactivo_Scene;
import com.jumpwheel.Scenes.City_Scene;
import com.jumpwheel.Scenes.Scene;

import java.io.IOException;

import static com.jumpwheel.Constants.HEIGHT;
import static com.jumpwheel.Constants.WIDTH;


/**
 * This is the screen that you see when you enter the game. It has a button for playing the game.
 * When you press this button, you go to the game screen so that you can start to play. This
 * screen was done by copying the code from GameOverScreen. All the cool comments have been
 * copy-pasted.
 */
public class MapsScreen extends BaseScreen {

    private Stage stage;

    private ScrollPane scrollPane;

    private Table container, table[];

    private Button play;

    private float ancho =(WIDTH /8f);

    private float alto = (HEIGHT /4f);

    private Pair<Texture, MapSelect.Mapa> scenes[];

    private int num_scenes;

    private float proporcion_x = WIDTH /640f;

    private float scrollx;

    private float ancho_t = ancho*3f,  alto_t = alto*1.5f;
    private float ancho_g = ancho*3.5f,  alto_g = alto*1.75f;
    private float pad = 15*proporcion_x;
    private Scene scene;
    private float origen_x;
    private Texture titulo, fondo;

    public MapsScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        origen_x = 0;

        int play_ancho = (int)(ancho*3f);
        int play_alto = (int)(alto*0.75f);

        play = new ButtonPlay(WIDTH /2-play_ancho/2, (int)alto/4, play_ancho, play_alto, game);

        titulo = game.getManager().get("jumpwheel.png", Texture.class);
        fondo = game.getManager().get("fondo_map.jpg", Texture.class);
        this.LoadScenes();

        container = new Table();
        container.setWidth(WIDTH);
        container.setHeight(HEIGHT);

        table = new Table[num_scenes];

        for(int i = 0; i < num_scenes; i++){
            table[i] = new Table();
            table[i].add(new Image(scenes[i].getFirst())).size(ancho_t,alto_t);
            table[i].getCells().get(0).getActor().setColor(0.8f, 0.8f, 0.8f ,0.7f);
        }

        Table innerContainer = new Table();

        innerContainer.add(table[0]).pad(pad).padLeft(2.5f*ancho);

        for(int i = 1; i < num_scenes-1; i++)
            innerContainer.add(table[i]).pad(pad);

        innerContainer.add(table[num_scenes-1]).pad(pad).padRight(2.5f*ancho);

        scrollPane = new ScrollPane(innerContainer);
        scrollPane.setScrollingDisabled(false, true);
        scrollx = scrollPane.getScrollX();

        container.add(scrollPane).fill().expand().center();

        stage.addActor(container);

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
        origen_x+=6;
        stage.getBatch().begin();
        stage.getBatch().setColor(1,1,1,1f);
        stage.getBatch().draw(fondo, 0, 0, WIDTH, HEIGHT);
        play.draw(stage.getBatch());
        stage.getBatch().draw(titulo, WIDTH*0.15f, HEIGHT-HEIGHT*0.22f, WIDTH*0.7f, HEIGHT/6f);
        stage.getBatch().end();

        for(int i = 0; i < num_scenes; i++){
            Vector2 coords = new Vector2(table[i].getX(), table[i].getY());
            float n = table[i].localToStageCoordinates(coords).x - table[i].getX();
            if(n >= ancho*4-pad-ancho_t && n < ancho*4+pad){
                agrandar(table[i]);
                if(MapSelect.map != scenes[i].getSecond())
                    load_scene(i);
            }
            else{
                disminuir(table[i]);
            }
        }

        float velocity_x = Math.abs(scrollx-scrollPane.getScrollX());

        if(velocity_x < 2 && !Gdx.input.isTouched()){
            for(int i = 0; i < num_scenes; i++){
                if(table[i].getCells().get(0).getActor().getWidth() == ancho_g){
                    Vector2 coords = new Vector2(table[i].getX(), table[i].getY());
                    float pos = table[i].localToStageCoordinates(coords).x - table[i].getX();
                    scrollPane.setVelocityX(0);

                    if(pos > 2.5f*ancho+5)
                        scrollPane.setScrollX(scrollPane.getScrollX()+5);
                    else if(pos < 2.5f*ancho-5)
                        scrollPane.setScrollX(scrollPane.getScrollX()-5);
                }
            }
        }

        scrollx = scrollPane.getScrollX();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //cuando se cambie de tamaño se redimensionara el juego
        Gdx.gl.glViewport( 0, 0, width, height);
    }

    private void agrandar(Table table){
        float _x = table.getCells().get(0).getActor().getX();
        float _y = table.getCells().get(0).getActor().getY();

        if(table.getCells().get(0).getActor().getWidth() == ancho_t)
            table.getCells().get(0).getActor().setPosition(_x-(ancho_g-ancho_t)/2,_y-(alto_g-alto_t)/2);

        table.getCells().get(0).getActor().setSize(ancho_g, alto_g);
        table.getCells().get(0).getActor().setColor(1,1,1,1);
    }

    private void disminuir(Table table){
        float _x = table.getCells().get(0).getActor().getX();
        float _y = table.getCells().get(0).getActor().getY();

        if(table.getCells().get(0).getActor().getWidth() == ancho_g)
            table.getCells().get(0).getActor().setPosition(_x+(ancho_g-ancho_t)/2,_y+(alto_g-alto_t)/2);

        table.getCells().get(0).getActor().setSize(ancho_t,alto_t);
        table.getCells().get(0).getActor().setColor(0.8f, 0.8f, 0.8f ,0.7f);
    }

    private void LoadScenes(){
        num_scenes = 5;
        scenes = new Pair[num_scenes];
        String select = "Select_map/";
        scenes[0] = new Pair<Texture, MapSelect.Mapa>(new Texture(select+"china_select.png"), MapSelect.Mapa.CHINA);
        scenes[1] = new Pair<Texture, MapSelect.Mapa>(new Texture(select+"tenebroso_select.png"), MapSelect.Mapa.HALLOWEEN);
        scenes[2] = new Pair<Texture, MapSelect.Mapa>(new Texture(select+"mutantes_select.png"), MapSelect.Mapa.MUTANTES);
        scenes[3] = new Pair<Texture, MapSelect.Mapa>(new Texture(select+"central_nuclear_select.png"), MapSelect.Mapa.RADIACTIVO);
        scenes[4] = new Pair<Texture, MapSelect.Mapa>(new Texture(select+"robot_select.png"), MapSelect.Mapa.ROBOT);
    }

    public Scene SceneSelected() throws IOException {
        if(MapSelect.map == MapSelect.Mapa.CHINA) return new China_Scene(game);
        else if(MapSelect.map == MapSelect.Mapa.HALLOWEEN) return new Halloween_Scene(game);
        else if(MapSelect.map == MapSelect.Mapa.MUTANTES)return  new Mutantes_Scene(game);
        else if(MapSelect.map == MapSelect.Mapa.RADIACTIVO)return new Radiactivo_Scene(game);
        else return new City_Scene(game);
    }

    public void load_scene(int i){
        origen_x = 0;
        MapSelect.map = scenes[i].getSecond();
        try {
            scene = this.SceneSelected();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}