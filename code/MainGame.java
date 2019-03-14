package com.jumpwheel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {
	private AssetManager manager;
	public BaseScreen loadingScreen, menuScreen, gameScreen, mapsScreen;
	public ClienteTCP clienteTCP;

	@Override
	public void create() {
		clienteTCP = new ClienteTCP();
		manager = new AssetManager();
		manager.load("inicio.jpg", Texture.class);
		manager.load("wheel.png", Texture.class);
		manager.load("cono.png", Texture.class);
		manager.load("marron.png", Texture.class);
		manager.load("tenebroso_suelo.png", Texture.class);
		manager.load("buda.png", Texture.class);

		/* CHINA */
		manager.load("China/china.png", Texture.class);
		manager.load("China/cielo_china.jpg", Texture.class);
		manager.load("China/cerezos.png", Texture.class);
		manager.load("China/china_floor.png", Texture.class);
		manager.load("China/fuji.png", Texture.class);

		/* HALLOWEEN */
		manager.load("Halloween/arboles_halloween.png", Texture.class);
		manager.load("Halloween/cielo_halloween.jpg", Texture.class);
		manager.load("Halloween/cementerio1.png", Texture.class);
		manager.load("Halloween/halloween2.png", Texture.class);
		manager.load("Halloween/halloween_floor.png", Texture.class);
		manager.load("Halloween/bosque.png", Texture.class);

		/* RADIOACTIVO */
		manager.load("Radioactivo/central_nuclear.png", Texture.class);
		manager.load("Radioactivo/pinos.png", Texture.class);
		manager.load("Radioactivo/cielo_radioactivo.jpg", Texture.class);
		manager.load("Radioactivo/radioactive_floor.png", Texture.class);

		/* MUTANTES */
		manager.load("Mutantes/ventanas.jpg", Texture.class);
		manager.load("Mutantes/mutants_floor.png", Texture.class);

		/* CIUDAD */
		manager.load("Ciudad/cielo_azul.jpg", Texture.class);
		manager.load("Ciudad/ciudad1.png", Texture.class);
		manager.load("Ciudad/ciudad2.png", Texture.class);
		manager.load("Ciudad/city_floor.png", Texture.class);

		manager.load("numeros.png", Texture.class);
		manager.load("letras.png", Texture.class);
		manager.load("barra.png", Texture.class);
		manager.load("ventana_menu.png", Texture.class);
		manager.load("fondo_map.jpg", Texture.class);
		manager.load("jumpwheel.png", Texture.class);
		manager.load("Fuego/1.png", Texture.class);
		manager.load("Fuego/2.png", Texture.class);
		manager.load("Fuego/3.png", Texture.class);
		manager.load("Fuego/4.png", Texture.class);
		manager.load("rojo.png", Texture.class);

		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void finishLoading() {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		mapsScreen = new MapsScreen(this);
		setScreen(menuScreen);
	}

	public AssetManager getManager() {
		return manager;
	}

}