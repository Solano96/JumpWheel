package com.jumpwheel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.jumpwheel.Buttons.Button;
import com.jumpwheel.Buttons.ButtonExitScene;
import com.jumpwheel.Buttons.ButtonResume;
import com.jumpwheel.Buttons.ButtonRetry;

import static com.jumpwheel.Constants.*;

/**
 * Created by FcoSolano on 12/09/2016.
 */
public class PauseScreen {
    private MainGame game;
    private GameScreen screen;
    private Button retry, back;
    private ButtonResume resume;
    Texture ventana_menu;

    private int ancho = (int)(WIDTH /8);
    private int alto = (int)(HEIGHT /4);

    private int play_ancho = (int)(ancho*3f);
    private int play_alto = (int)(alto*0.75f);

    private int botton_centered = ancho*4-play_ancho/2;
    private float borde_rel;
    private float prop_alto;
    float r;

    public PauseScreen(MainGame game, GameScreen screen){
        ventana_menu = game.getManager().get("ventana_menu.png", Texture.class);
        borde_rel = 0;
        prop_alto = 1.0f*GAME_HEIGHT/HEIGHT;
        r = 1.3f;
        // Boton para continuar
        resume = new ButtonResume(botton_centered, (int)(HEIGHT *0.53f), play_ancho, play_alto, game, screen){
            @Override
            public void draw(Batch batch){
                float alto;
                float mitad_ancho = bordes.x+bordes.width/2f;
                float mitad_alto = (bordes.y+bordes.height/2f)*prop_alto;
                Texture boton = boton_sin_pulsar;

                if(!pulsado)
                    alto = bordes.height * prop_alto / DIV_SIN_PULSAR/r;
                else{
                    alto = bordes.height*prop_alto/DIV_PULSADO/r;
                    boton = boton_pulsado;
                }

                float ancho = frase.anchoCadena(cadena, alto);
                float pos_x = mitad_ancho-(ancho)/2f;
                float pos_y = mitad_alto-alto/2f;
                batch.draw(boton, bordes.x, bordes.y*prop_alto, bordes.width, bordes.height*prop_alto);
                frase.writeCadena(batch, cadena, pos_x, pos_y, alto);
            }
        };
        // Boton para reiniciar
        retry = new ButtonRetry(botton_centered, (int)(HEIGHT *0.33f), play_ancho, play_alto, game, screen){
            @Override
            public void draw(Batch batch){
                float alto;
                float mitad_ancho = bordes.x+bordes.width/2f;
                float mitad_alto = (bordes.y+bordes.height/2f)*prop_alto;
                Texture boton = boton_sin_pulsar;

                if(!pulsado)
                    alto = bordes.height * prop_alto / DIV_SIN_PULSAR/r;
                else{
                    alto = bordes.height*prop_alto/DIV_PULSADO/r;
                    boton = boton_pulsado;
                }

                float ancho = frase.anchoCadena(cadena, alto);
                float pos_x = mitad_ancho-(ancho)/2f;
                float pos_y = mitad_alto-alto/2f;
                batch.draw(boton, bordes.x, bordes.y*prop_alto, bordes.width, bordes.height*prop_alto);
                frase.writeCadena(batch, cadena, pos_x, pos_y, alto);
            }
        };
        // Boton para volver atras
        back = new ButtonExitScene(botton_centered, (int)(HEIGHT *0.13f), play_ancho, play_alto, game){
            @Override
            public void draw(Batch batch){
                float alto;
                float mitad_ancho = bordes.x+bordes.width/2f;
                float mitad_alto = (bordes.y+bordes.height/2f)*prop_alto;
                Texture boton = boton_sin_pulsar;

                if(!pulsado)
                    alto = bordes.height * prop_alto / DIV_SIN_PULSAR/r;
                else{
                    alto = bordes.height*prop_alto/DIV_PULSADO/r;
                    boton = boton_pulsado;
                }

                float ancho = frase.anchoCadena(cadena, alto);
                float pos_x = mitad_ancho-(ancho)/2f;
                float pos_y = mitad_alto-alto/2f;
                batch.draw(boton, bordes.x, bordes.y*prop_alto, bordes.width, bordes.height*prop_alto);
                frase.writeCadena(batch, cadena, pos_x, pos_y, alto);
            }
        };
    }

    public void update(float x){
        borde_rel = x;
        // Obtener la posicion de los rectangulos correspodientes a los botones
        Rectangle r_resume = new Rectangle(x+botton_centered, resume.getBordes().getY(), play_ancho, play_alto);
        Rectangle r_retry = new Rectangle(x+botton_centered, retry.getBordes().getY(), play_ancho, play_alto);
        Rectangle r_back = new Rectangle(x+botton_centered, back.getBordes().getY(), play_ancho, play_alto);

        // Actualizamos los bordes de los botones
        resume.setBordes(r_resume);
        retry.setBordes(r_retry);
        back.setBordes(r_back);

        resume.update();
        retry.update();
        back.update();
    }

    public void draw(Batch batch){
        // Dibujamos los botones
        resume.draw(batch);
        retry.draw(batch);
        back.draw(batch);
    }
}
