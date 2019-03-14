package com.jumpwheel;

/**
 * Created by MiToshiba on 28/08/2016.
 */
public final class RPantalla {

    //portrait
    private float anchop;
    private float altop;
    //landscape
    private float anchol;
    private float altol;
    //Coordenas X,Y para las diferentes vistas
    //portrait
    private float xp=0f;
    private float yp=0f;
    //landscape
    private float xl=0f;
    private float yl=0f;
    //Posicion de vista por defecto
    private String posicion="portrait" ;

    /**
     * Constructor de clase
     * @param game_ancho Ancho del juego
     * @param game_alto Alto del juego
     * @param screen_ancho Ancho de la pantalla del celular
     * @param screen_alto Alto de la pantalla del celular
     * */
    public RPantalla( float game_ancho, float game_alto ,float screen_ancho, float screen_alto  )
    {
   /* Portrait
 |--------|
 | |
 | |
 | |
 | |
 y |
 |x-------|
 */
        //posicion
        xp = 0f;
        yp = (screen_alto - game_alto*(screen_ancho/game_ancho))/2f;
        //tamaño
        anchop =  game_ancho*(screen_ancho/game_ancho);
        altop = game_alto*(screen_ancho/game_ancho);

   /* Lanscape
 |-------------------|
 | |
 | |
 y |
 |x------------------|
 */
        //posicion
        xl= ( screen_ancho - game_ancho*(screen_alto/game_alto))/2f;
        yl = 0f;
        //tamaño
        anchol = game_ancho*(screen_alto/game_alto);
        altol = game_alto*(screen_alto/game_alto);

    }

    /**
     * Segun las dimensiones de la pantalla asigna la posicion en portrait o landscape
     * @param width Ancho de pantalla
     * @param height Alto de pantalla
     * */
    public void setPosicion( int width, int height )
    {
        this.posicion = ( width > height )?"landscape":"portrait" ;
    }

    public int getX()
    {
        return ( posicion.equals("landscape"))?(int)xl :(int)xp;
    }

    public int getY()
    {
        return ( posicion.equals("landscape"))?(int)yl :( int)yp;
    }

    public int getAncho()
    {

        return ( posicion.equals("landscape"))?(int)anchol :( int)anchop;
    }

    public int getAlto()
    {
        return ( posicion.equals("landscape"))?(int)altol :( int)altop;
    }

}

