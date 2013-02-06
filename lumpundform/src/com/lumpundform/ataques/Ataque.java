package com.lumpundform.ataques;

import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.actores.Personaje;

/**
 * La base para todos los ataques. Todos los {@link Ataque} inicializados tienen
 * que ser una sublcase de esta clase.
 * 
 * @author Sergio Valencia
 * 
 */
public class Ataque extends ObjetoActor {
	// Valores estáticos de los personajes
	protected class Estado {
		public static final String NORMAL = "normal";
		public static final String DEFAULT = "normal";
		public static final String EXPLOTANDO = "explosion";
	}

	// Personaje
	private Personaje personaje;

	private boolean destruir = false;
	private float dano;
	private boolean haceDano = true;

	/**
	 * Crea el {@link Ataque} y lo asigna al {@link Personaje} al que pertenece.
	 * 
	 * @param nombre
	 *            El nombre del {@link Ataque}, para poderlo referenciar.
	 * @param personaje
	 *            El {@link Personaje} al que pertenece el {@link Ataque}.
	 */
	protected Ataque(String nombre, Personaje personaje) {
		super(nombre);

		setEstadoDefault(Estado.DEFAULT);

		setPersonaje(personaje);
		setPosicionCentro(personaje.getPosicionCentro());
	}

	/**
	 * Marca al ataque como destruido para evitar que haga daño.
	 */
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
