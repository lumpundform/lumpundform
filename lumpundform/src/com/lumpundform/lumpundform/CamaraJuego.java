package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que extiende a {@link OrthographicCamera} y agrega funciones para
 * posicionar la cámara
 * 
 * @author Sergio
 * 
 */
public class CamaraJuego extends OrthographicCamera {
	private Vector2 posicionOrigen = new Vector2();

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
		position.x = x;
		position.y = y;
		posicionOrigen.x = x - viewportWidth / 2;
		posicionOrigen.y = y - viewportHeight / 2;
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
}
