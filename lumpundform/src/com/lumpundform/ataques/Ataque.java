package com.lumpundform.ataques;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.actores.Personaje;

public abstract class Ataque extends ObjetoActor {
	// Valores estáticos de los personajes
	protected static enum Estado {
		NORMAL, EXPLOTANDO
	}
	
	// Estado
	private Estado estado;
	
	private boolean destruir = false;
	private float dano;
	private boolean haceDano = true;

	protected Ataque(String nombre, Personaje personaje) {
		super(nombre);

		setPosicionCentro(personaje.getPosicionCentro());
	}

	@Override
	protected TextureRegion getCuadroActual() {
		// Revisar de cual animación se va a agarrar el cuadro actual
		String nombreAnimacion;
		boolean loop;
		switch (getEstado()) {
		case NORMAL:
		default:
			nombreAnimacion = "normal";
			loop = true;
			break;
		case EXPLOTANDO:
			nombreAnimacion = "explosion";
			loop = false;
			if (getTiempoTranscurrido() > getAnimacion().get(nombreAnimacion).frameDuration * 10) {
				remove();
			}
			break;
		}

		if (!getAnimacion().containsKey(nombreAnimacion)) {
			nombreAnimacion = "normal";
		}

		return getAnimacion().get(nombreAnimacion).getKeyFrame(getTiempoTranscurrido(),
				loop);
	}

	public void destruir() {
		setHaceDano(false);
		setDestruir(true);
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public float getDano() {
		return dano;
	}

	public void setDano(float dano) {
		this.dano = dano;
	}

	public boolean isDestruir() {
		return destruir;
	}

	public void setDestruir(boolean destruir) {
		this.destruir = destruir;
	}

	public boolean isHaceDano() {
		return haceDano;
	}

	public void setHaceDano(boolean haceDano) {
		this.haceDano = haceDano;
	}

}
