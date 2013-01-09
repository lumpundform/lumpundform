package com.lumpundform.pociones;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.colision.Rectangulo;

public class PocionBase extends ObjetoActor {
	public static float cantidad = 20.0f;

	public PocionBase(String nombre, Vector2 posicion) {
		super(nombre);

		setX(posicion.x);
		setY(posicion.y);
		setWidth(15.0f);
		setHeight(20.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));

		setCaer(true);

		cargarAnimaciones("normal");
	}

	@Override
	protected TextureRegion getCuadroActual() {
		return getAnimacion().get("normal").getKeyFrame(getTiempoTranscurrido(), true);
	}

}
