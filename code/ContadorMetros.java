package com.jumpwheel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.jumpwheel.Constants.*;

/**
 * Created by FcoSolano on 22/09/2016.
 */
public class ContadorMetros {
    private int metros;
    private Texture numeros;
    private Texture barra;
    private TextureRegion numero[];
    private MainGame game;

    public ContadorMetros(MainGame game){
        numeros = game.getManager().get("numeros.png", Texture.class);
        barra = game.getManager().get("barra.png", Texture.class);
        this.game = game;
        numero = new TextureRegion[10];

        for(int i = 0; i < 10; i++)
            numero[i] = new TextureRegion(numeros, i*(numeros.getWidth()/10), 0, (numeros.getWidth()/10), numeros.getHeight());

        metros = 0;
    }

    public void setMetros(int m){
        this.metros = m;
    }

    public void draw(Batch batch, float cero_relativo){
        int n = cuentaCifras(metros);
        int cifras[] = new int[n];
        int aux = metros;

        for(int i = 0; i < n-1; i++){
            cifras[i] = (int) (aux/Math.pow(10, (n-i-1)));
            aux%=Math.pow(10, (n-i-1));
        }
        cifras[n-1] = aux;

        float ancho_num = GAME_WIDTH/25f;
        float alto_num = GAME_HEIGHT/12.5f;

        batch.begin();
        Color c = batch.getColor();
        batch.setColor(1,1,1,1f);
        for(int i = 0; i < n; i++){
            draw_numero(batch, i*ancho_num+20+cero_relativo, cifras[i], ancho_num, alto_num);
        }
        batch.draw(barra, n*ancho_num+20+cero_relativo, GAME_HEIGHT-alto_num*2, ancho_num, alto_num);

        int meta[] = {1,0,0,0};
        for(int i = n+1; i < n+5; i++){
            draw_numero(batch, i*ancho_num+20+cero_relativo, meta[i-n-1], ancho_num, alto_num);
        }
        batch.setColor(c);
        batch.end();
    }

    private void draw_numero(Batch batch, float pos, int num, float ancho, float alto){
        TextureRegion number = new TextureRegion(numero[num]);
        batch.draw(number, pos, GAME_HEIGHT-alto*2, ancho, alto);
    }

    private int cuentaCifras(int m){
        int resultado = m/10;
        int num_cifras = 1;

        while(resultado > 0){
            resultado /= 10;
            num_cifras++;
        }
        return num_cifras;
    }
}
