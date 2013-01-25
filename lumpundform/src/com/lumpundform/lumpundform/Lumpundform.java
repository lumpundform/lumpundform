package com.lumpundform.lumpundform;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lumpundform.input.ProcesadorEntradaGeneral;
import com.lumpundform.pantallas.PantallaBase;
import com.lumpundform.pantallas.PantallaJuego;

public class Lumpundform extends Game {
	PantallaJuego pantallaJuego;

	@Override
	public void create() {
		// TODO: cambiar el nivel del log para que no salgan todos los errores
		// en consola
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		setInputProcessor();

		// TODO: no comenzar el juego con la pantalla de juego
		pantallaJuego = new PantallaJuego();
		setScreen(pantallaJuego);
	}

	public void reset() {
		setInputProcessor();
		if (getScreen() != null)
			((PantallaBase) getScreen()).reset();
	}

	private void setInputProcessor() {
		Gdx.input.setInputProcessor(new ProcesadorEntradaGeneral(this));
	}

}
