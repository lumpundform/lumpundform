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

		estado = Estado.DETENIDO;
		destinoX = x;
		direccionX = Direccion.DERECHA;
		velocidad = 200;

		vida = 100.0f;
		vidaMaxima = 100.0f;

		cargarAnimaciones("detenido", "corriendo");
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (x <= 0) {
			direccionX = Direccion.DERECHA;
		} else if (x + width >= Gdx.graphics.getWidth()) {
			direccionX = Direccion.IZQUIERDA;
		}

		if (derecha()) {
			moverDerecha(delta);
		} else {
			moverIzquierda(delta);
		}
	}

}
