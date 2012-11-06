package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ataque extends ObjetoActor {
	Personaje personaje;
	boolean destruir = false;

	protected Ataque(String nombre, Personaje personaje) {
		super(nombre);

		this.personaje = personaje;
		setPosicionCentro(personaje.getPosicionCentro());
	}

	@Override
	protected abstract void cargarAnimaciones();

	@Override
	protected TextureRegion getCuadroActual() {
		// Revisar de cual animación se va a agarrar el cuadro actual
		String nombreAnimacion;
		boolean loop;
		switch (estado) {
		case NORMAL:
		default:
			nombreAnimacion = "normal";
			loop = true;
			break;
		case EXPLOTANDO:
			nombreAnimacion = "explosion";
			loop = false;
			if (tiempoTranscurrido > animacion.get(nombreAnimacion).frameDuration * 10) {
				remove();
			}
			break;
		}

		if (!animacion.containsKey(nombreAnimacion)) {
			nombreAnimacion = "normal";
		}

		return animacion.get(nombreAnimacion).getKeyFrame(tiempoTranscurrido,
				loop);
	}

	public void destruir() {
		destruir = true;
	}

}
