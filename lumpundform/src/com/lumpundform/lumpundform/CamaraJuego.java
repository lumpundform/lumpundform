package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Clase que extiende a {@link OrthographicCamera} y agrega funciones para
 * posicionar la cámara
 * 
 * @author Sergio
 * 
 */
public class CamaraJuego extends OrthographicCamera {
	private Vector2 posicionOrigen = new Vector2();
	private boolean bloqueada = false;

	public CamaraJuego() {
		super();

		setPosicion(position.x, position.y);
	}

	/**
	 * Posiciona la {@link CamaraJuego} desde el centro
	 * 
	 * @param x
	 *            Posición x para posicionar la cámara
	 * @param y
	 *            Posición y para posicionar la cámara
	 */
	public void setPosicion(float x, float y) {
		if (!isBloqueada()) {
			position.x = x;
			position.y = y;
			posicionOrigen.x = x - viewportWidth / 2;
			posicionOrigen.y = y - viewportHeight / 2;
		}
	}

	/**
	 * Posiciona la {@link CamaraJuego} desde el origen
	 * 
	 * @param x
	 *            Posición x para posicionar la cámara
	 * @param y
	 *            Posición y para posicionar la cámara
	 */
	public void setPosicionOrigen(float x, float y) {
		posicionOrigen.x = x;
		posicionOrigen.y = y;
		position.x = x + viewportWidth / 2;
		position.y = y + viewportHeight / 2;
	}

	/**
	 * Regresa la posición origen de la {@link CamaraJuego}
	 * 
	 * @return La posición de la cámara
	 */
	public Vector2 getPosicionOrigen() {
		return posicionOrigen;
	}

	/**
	 * Manda llamar a {@link #unproject(Vector3)} de {@link Camera} pero sin
	 * necesidad de pasar un {@link Vector3}.
	 * 
	 * @param vector
	 *            El vector a coonvertir.
	 * @return El vector ya con las coordenadas correctas.
	 */
	public Vector2 unproject(Vector2 vector) {
		Vector3 pos = new Vector3(vector.x, vector.y, 0);
		unproject(pos);

		return new Vector2(pos.x, pos.y);
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}
}
