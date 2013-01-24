package com.lumpundform.ataques;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.actores.Personaje;

public abstract class Ataque extends ObjetoActor {
	// Valores estáticos de los personajes
	protected class Estado {
		public static final String NORMAL = "normal";
		public static final String EXPLOTANDO = "explosion";
	}

	// Personaje
	private Personaje personaje;

	private boolean destruir = false;
	private float dano;
	private boolean haceDano = true;

	protected Ataque(String nombre, Personaje personaje) {
		super(nombre);

		setPersonaje(personaje);
		setPosicionCentro(personaje.getPosicionCentro());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (getEstado() == Estado.EXPLOTANDO) {
			setLoopAnimacion(false);
			if (getTiempoTranscurrido() > getAnimacion(getEstado()).frameDuration * 10) {
				remove();
			}

		}
		super.draw(batch, parentAlpha);
	}

	public void destruir() {
		setHaceDano(false);
		setDestruir(true);
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

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

}
