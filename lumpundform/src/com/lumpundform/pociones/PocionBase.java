package com.lumpundform.pociones;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.colision.Rectangulo;

public class PocionBase extends ObjetoActor {
	private float cantidad;

	public PocionBase(String nombre, Vector2 posicion) {
		super(nombre);

		setX(posicion.x);
		setY(posicion.y);
		setWidth(15.0f);
		setHeight(20.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));
		
		setCaer(true);
		
		setCantidad(20.0f);
		
		cargarAnimaciones("normal");
	}

	@Override
	protected TextureRegion getCuadroActual() {
		return getAnimacion().get("normal").getKeyFrame(getTiempoTranscurrido(), true);
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

}
