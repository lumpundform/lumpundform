package com.lumpundform.pociones;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.colision.Rectangulo;

public class PocionBase extends ObjetoActor {
	private String tipo;

	public PocionBase(Vector2 posicion, String tipo) {
		super("pocion_" + tipo);

		setTipo(tipo);

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
