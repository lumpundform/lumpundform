package com.lumpundform.lumpundform;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Lumpundform extends Game {
	private PantallaJuego pantallaJuego;

	@Override
	public void create() {
		// TODO: cambiar el nivel del log para que no salgan todos los errores
		// en consola
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// TODO: no comenzar el juego con la pantalla de juego
		pantallaJuego = new PantallaJuego();
		setScreen(pantallaJuego);
	}

}
