package com.lumpundform.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.colision.Rectangulo;

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

		setHitbox(new Rectangulo(height, width / 2));

		setEstado(Estado.DETENIDO);
		setDestinoX(x);
		setDireccionX(Direccion.DERECHA);
		setVelocidad(200);

		setVida(100.0f);
		setVidaMax(100.0f);

		cargarAnimaciones("detenido", "corriendo");
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (x <= 0) {
			setDireccionX(Direccion.DERECHA);
		} else if (x + width >= Gdx.graphics.getWidth()) {
			setDireccionX(Direccion.IZQUIERDA);
		}

		if (derecha()) {
			moverDerecha(delta);
		} else {
			moverIzquierda(delta);
		}
	}

}
