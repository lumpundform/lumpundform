package com.lumpundform.ataques;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.actores.Personaje;

public abstract class Ataque extends ObjetoActor {
	// Valores estáticos de los personajes
	public static enum Estado {
		NORMAL, EXPLOTANDO
	}
	
	// Estado
	protected Estado estado;
	
	protected boolean destruir = false;
	public float dano;
	public boolean haceDano = true;

	protected Ataque(String nombre, Personaje personaje) {
		super(nombre);

		setPosicionCentro(personaje.getPosicionCentro());
	}

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
		haceDano = false;
		destruir = true;
	}

}
