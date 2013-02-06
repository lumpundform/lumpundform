package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;

/**
 * El {@link Ataque} tipo misil.
 * 
 * @author Sergio Valencia
 * 
 */
public class AtaqueMisil extends Ataque {
	private boolean movimiento;

	/**
	 * Inicializa el {@link AtaqueMisil} así como todas sus características
	 * necesarias para su comportamiento.
	 * 
	 * @param personaje
	 *            El {@link Personaje} al que pertenece el {@link Ataque}.
	 */
	public AtaqueMisil(Personaje personaje) {
		super("ataque_misil", personaje);

		movimiento = true;

		setWidth(100.0f);
		setHeight(100.0f);

		setHitbox(new Rectangulo(getHeight() * 0.2f, getWidth() * 0.55f));

		setEstado(Estado.NORMAL);
		setVelocidad(800.0f);
		setDireccionX(personaje.getDireccionX());

		setDano(30.0f);

		setY(personaje.getY() + (personaje.getHitbox().getAlto() / 2) - 33);

		float xNueva;
		if (derecha()) {
			xNueva = personaje.getEsquina("inf-izq").x + 30;
		} else {
			xNueva = personaje.getEsquina("inf-izq").x - 40;
		}
		setEsquinaX("inf-izq", xNueva);

		setQuitarConAnimacion(true);

		cargarAnimaciones("normal");
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (movimiento) {
			if (derecha()) {
				moverDerecha(delta);
			} else {
				moverIzquierda(delta);
			}
		}
	}

	@Override
	public void destruir() {
		if (!isDestruir()) {
			super.destruir();
			movimiento = false;
			getAnimacion().terminar();
		}
	}

}
