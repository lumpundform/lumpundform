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

		setWidth(125.0f);
		setHeight(150.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth() / 2));

		setEstado(Estado.DETENIDO);
		setDestinoX(getX());
		setDireccionX(Direccion.DERECHA);
		setVelocidad(200);

		setVida(100.0f);
		setVidaMax(100.0f);

		cargarAnimaciones("detenido", "corriendo");
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (!isEnemigo()) {
			if (getX() <= 0) {
				setDireccionX(Direccion.DERECHA);
			} else if (getX() + getWidth() >= Gdx.graphics.getWidth()) {
				setDireccionX(Direccion.IZQUIERDA);
			}

			if (derecha()) {
				moverDerecha(delta);
			} else {
				moverIzquierda(delta);
			}
		}
	}

}
