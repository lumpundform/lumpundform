package com.lumpundform.lumpundform;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;

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

		// Detectar gestos con DetectorGestos
		Gdx.input.setInputProcessor(new InputMultiplexer(new ProcesadorEntrada(
				this), new GestureDetector(new ProcesadorEntrada(this))));
	}

}
