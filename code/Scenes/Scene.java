package com.jumpwheel.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jumpwheel.Constants;
import com.jumpwheel.entities.EntityFactory;
import com.jumpwheel.entities.FireEntity;
import com.jumpwheel.entities.FloorEntity;
import com.jumpwheel.entities.SpikeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.jumpwheel.Constants.*;

/**
 * Created by FcoSolano on 07/09/2016.
 */
public class Scene extends Actor{
    protected List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    protected List<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();
    protected List<FireEntity> fireList = new ArrayList<FireEntity>();
    protected Texture texture_floor, texture_spike;
    protected Texture fondo[];
    protected TextureRegion fondo_visible[];
    protected int num_capas = 1;
    protected boolean red_up = false, green_up = false, blue_up = false;
    protected boolean change_red = false, change_green = false, change_blue = false;
    protected float red = 1, blue = 1, green = 1;
    protected Music music;
    protected float k[];

    public Scene(){}

    public List<FloorEntity> suelo(){
        return floorList;
    }

    public List<SpikeEntity> spike(){
        return spikeList;
    }

    public List<FireEntity> fire(){
        return fireList;
    }

    public Texture fondo(int i){
        return fondo[i];
    }

    public int getNum_capas(){
        return num_capas;
    }

    public Texture getTexture_floor(){
        return texture_floor;
    }

    public Texture getTexture_spike(){
        return texture_spike;
    }

    protected float pause=1, origen_x=0;

    public Music getMusic() {
        return music;
    }

    public void setVariables(float pause, float origen_x){
        this.pause = pause;
        this.origen_x = origen_x;
    }

    public void draw(Batch batch, float delta){
            this.actualizar_capas(delta);
            this.draw(batch, true);
    }
/*
    public void draw(Batch batch){
        batch.setColor(red*pause, green*pause, blue*pause, 1f);

        for(int j = 0; j < 3;j++)
            batch.draw(fondo[num_capas-1], origen_x+fondo[num_capas-1].getWidth()*j-k[num_capas-1], 0, GAME_WIDTH* fondo[num_capas-1].getHeight()/GAME_HEIGHT, fondo[num_capas-1].getHeight());

        float t = 1f/(num_capas-1);
        for(int i = num_capas-2; i >= 0; i--){
            batch.setColor(red*pause, green*pause, blue*pause, t*(num_capas-1-i));
            for(int j = 0; j < 3; j++)
                batch.draw(fondo[i], origen_x+fondo[i].getWidth()*j-k[i], 0, GAME_WIDTH* fondo[i].getHeight()/GAME_HEIGHT, fondo[i].getHeight());
        }
        batch.setColor(pause, pause, pause, 1f);
    }

    private void actualizar_capas(float delta){
        change_color();

        for(int i = 0; i < num_capas; i++){
            float prop = fondo[i].getHeight()/480f;
            int desp = 50*(num_capas-i-1);
            k[i] = origen_x*prop*(0.02f*(num_capas-i))+desp;
            k[i] %= fondo[i].getWidth()/2;º
        }
    }*/

    public void draw(Batch batch, boolean isGame){
        batch.setColor(red*pause, green*pause, blue*pause, 1f);
        batch.draw(fondo_visible[num_capas-1], origen_x, 0, GAME_WIDTH, GAME_HEIGHT);

        float t = 1f/(num_capas-1);
        for(int i = num_capas-2; i >= 0; i--){
            batch.setColor(red*pause, green*pause, blue*pause, t*(num_capas-1-i));
            if(isGame)
               batch.draw(fondo_visible[i], origen_x, 0, GAME_WIDTH, GAME_HEIGHT);
            else
                batch.draw(fondo_visible[i], origen_x, 0, WIDTH, HEIGHT);
        }
        batch.setColor(pause, pause, pause, 1f);
    }

    private void actualizar_capas(float delta){
            change_color();

            float n[] = new float[num_capas];
            fondo_visible = new TextureRegion[num_capas];

            for(int i = 0; i < num_capas; i++){
                float prop = fondo[i].getHeight()/480f;
                int desp = 50*(num_capas-i-1);
                n[i] = origen_x*prop*(0.02f*(num_capas-i))+desp;
                n[i] %= fondo[i].getWidth()/2;
            }

            for(int i = 0; i < num_capas; i++) {
                fondo_visible[i] = new TextureRegion(fondo[i], (int)n[i], 0, GAME_WIDTH* fondo[i].getHeight()/GAME_HEIGHT, fondo[i].getHeight());
            }
    }

    public void move(Batch batch, float move){
        change_color();

        float n[] = new float[num_capas];
        fondo_visible = new TextureRegion[num_capas];

        for(int i = 0; i < num_capas; i++){
            float prop = fondo[i].getHeight()/480f;
            int desp = 50*(num_capas-i-1);
            n[i] = move*prop*(0.02f*(num_capas-i))+desp;
            n[i] %= fondo[i].getWidth()/2;
        }

        for(int i = 0; i < num_capas; i++) {
            fondo_visible[i] = new TextureRegion(fondo[i], (int)n[i], 0, GAME_WIDTH* fondo[i].getHeight()/GAME_HEIGHT, fondo[i].getHeight());
        }
        this.draw(batch, false);
    }
    // Cambiar tonos rojos
    private void change_red(){
            if(red_up){ red+=0.001f; if(red >= 0.95f) red_up = false;}
            else{ red-=0.001f; if(red <= 0) red_up = true; }
    }

    // Cambiar tonos verdes
    private void change_green(){
            if(green_up){ green+=0.001f; if(green >= 0.95f) green_up = false;}
            else{ green-=0.001f; if(green <= 0) green_up = true; }
    }

    // Cambiar tonos azules
    private void change_blue(){
            if(blue_up){ blue+=0.001f; if(blue >= 0.95f) blue_up = false;}
            else{blue-=0.001f; if(blue <= 0) blue_up = true; }
    }

    private void change_color(){
        if(change_red) this.change_red();
        if(change_green) this.change_green();
        if(change_blue) this.change_blue();
    }

    protected void GetSpike(String archivo, EntityFactory factory, World world){
        FileHandle file = Gdx.files.internal(archivo);
        String text = file.readString(), cadena;
        Scanner scanner = new Scanner(text);
        int alto = 10;

        while (scanner.hasNextLine()){
            cadena = scanner.nextLine();
            for (int i = 0; i < cadena.length(); i++)
                if(cadena.charAt(i) == 'A')
                    spikeList.add(factory.createSpikes(world, i, alto));

            alto--;
        }
    }

    protected void GetFire(String archivo, EntityFactory factory, World world){
        FileHandle file = Gdx.files.internal(archivo);
        String text = file.readString(), cadena;
        Scanner scanner = new Scanner(text);
        int alto = 10;

        while (scanner.hasNextLine()){
            cadena = scanner.nextLine();
            for (int i = 0; i < cadena.length(); i++)
                if(cadena.charAt(i) == 'F')
                    fireList.add(factory.createFire(world, i, alto));

            alto--;
        }
    }

    protected void GetFloor(String archivo, EntityFactory factory, World world){
        FileHandle file = Gdx.files.internal(archivo);
        String text = file.readString(), cadena;
        Scanner scanner = new Scanner(text);
        int alto = 10;
        boolean hay_suelo = false;
        int longitud = 0, origen = 0;

        while(scanner.hasNextLine()){
            cadena = scanner.nextLine();
            for (int i = 0; i < cadena.length(); i++){
                if(cadena.charAt(i) == 'x' && longitud==0){
                    longitud++;
                    origen = i;
                    hay_suelo = true;
                }
                else if(cadena.charAt(i) == 'x' && hay_suelo)
                    longitud++;

                if(hay_suelo && (cadena.charAt(i) != 'x' || i == cadena.length()-1)){
                    floorList.add(factory.createFloor(world, origen, longitud, alto));
                    hay_suelo = false;
                    longitud = 0;
                }
            }
            alto--;
        }
    }
}
