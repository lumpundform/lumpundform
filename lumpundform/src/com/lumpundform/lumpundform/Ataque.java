package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ataque extends ObjetoActor {
	Personaje personaje;

	protected Ataque(String nombre, Personaje personaje) {
		super(nombre);

		this.personaje = personaje;
		this.x = personaje.getPosicionCentro().x;
		this.y = personaje.getPosicionCentro().y;
	}

	@Override
	protected abstract void cargarAnimaciones();

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub

	}

	@Override
	protected TextureRegion getCuadroActual() {
		// TODO Auto-generated method stub
		return null;
	}

}
