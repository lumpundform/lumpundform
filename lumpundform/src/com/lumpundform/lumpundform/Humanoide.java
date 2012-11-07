package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase específica para los humanoides del juego
 * 
 * @author Sergio
 * 
 */
public class Humanoide extends Personaje {

	/**
	 * Carga datos específicos de {@link Humanoide}
	 * 
	 * @param nombre
	 *            El nombre del {@link ObjetoActor}
	 * @param puntoOrigen
	 *            En donde se va a originar el {@link ObjetoActor}
	 */
	public Humanoide(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);

		width = 125.0f;
		height = 150.0f;

		hitbox = new Rectangulo(height, width / 2, true);

		estado = DETENIDO;
		destinoX = x;
		direccionX = DERECHA;
		velocidad = 200;

		vida = 100.0f;

		cargarAnimaciones();
	}

	@Override
	protected void cargarAnimaciones() {
		try {
			animacion.put("detenido", initAnimacion("detenido"));
			animacion.put("corriendo", initAnimacion("corriendo"));
		} catch (DatoInexistenteException e) {
			U.err(e);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (x <= 0) {
			direccionX = DERECHA;
		} else if (x + width >= Gdx.graphics.getWidth()) {
			direccionX = IZQUIERDA;
		}

		if (direccionX == IZQUIERDA) {
			moverIzquierda(delta);
		} else if (direccionX == DERECHA) {
			moverDerecha(delta);
		}
	}

}
