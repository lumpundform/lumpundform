package com.lumpundform.ataques;

import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.actores.Personaje;
import com.lumpundform.habilidades.Habilidad;
import com.lumpundform.lumpundform.CamaraJuego;
import com.lumpundform.utilerias.U;

/**
 * La base para todos los ataques. Todos los {@link Ataque}s inicializados
 * tienen que ser una sublcase de esta clase.
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

	// Habilidad
	private Habilidad habilidad;

	// Daño
	private float dano;

	// Banderas
	private boolean destruir = false;
	private boolean haceDano = true;

	/**
	 * Crea el {@link Ataque} y lo asigna al {@link Personaje} al que pertenece.
	 * 
	 * @param nombre
	 *            El nombre del {@link Ataque}, para poderlo referenciar.
	 * @param personaje
	 *            El {@link Personaje} al que pertenece el {@link Ataque}.
	 */
	protected Ataque(String nombre, Personaje personaje, Habilidad habilidad) {
		super(nombre);

		setHabilidad(habilidad);

		setEstadoDefault(Estado.DEFAULT);

		setPersonaje(personaje);
		setPosicionCentro(personaje.getPosicionCentro());

		addAction(new AtaqueAction());
	}

	/**
	 * Revisa le colisión de los {@link Ataque}s del escenario con los
	 * {@link Personaje}s del mismo para hacer daño. Si un ataque choca con un
	 * escudo, se destruye sin hacer daño.
	 * 
	 * @return Si se destruyo el ataque.
	 */
	public boolean calcularColision() {
		for (Personaje personaje : getActoresEscenario(Personaje.class)) {
			if (personaje.isEnemigo() != getPersonaje().isEnemigo()
					&& getHitbox().estaColisionando(personaje.getHitbox())) {
				if (isHaceDano()) {
					personaje.quitarVida(getDano());
				}
				if (!getHabilidad().isSostenido()) {
					destruir();
					return true;
				}
			}
		}
		for (Ataque ataque : getActoresEscenario(Ataque.class)) {
			if (AtaqueEscudo.class.isInstance(ataque)
					&& ataque.getPersonaje().isEnemigo() != getPersonaje().isEnemigo()
					&& ataque.getHitbox().estaColisionando(getHitbox())) {
				destruir();
				return true;
			}
		}
		return false;
	}

	/**
	 * Quita todos los ataques que no están dentro de la vista de la
	 * {@link CamaraJuego}.
	 * 
	 * @return Si se destruyó el ataque.
	 */
	public boolean destruirSiFueraDeCamara() {
		CamaraJuego camara = U.getCamara();
		if ((getX() + getWidth()) < camara.getPosicionOrigen().x
				|| getX() > (camara.getPosicionOrigen().x + camara.viewportWidth)) {
			quitar();
			return true;
		}
		return false;
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

	public Habilidad getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}

}
