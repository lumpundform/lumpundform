package com.lumpundform.lumpundform;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Lumpundform extends Game {
	private PantallaJuego pantallaJuego;

	@Override
	public void create() {
		Texture.setEnforcePotImages(false);

		// TODO: cambiar el nivel del log para que no salgan todos los errores
		// en consola
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		pantallaJuego = new PantallaJuego(this);
		setScreen(pantallaJuego);
	}
}
