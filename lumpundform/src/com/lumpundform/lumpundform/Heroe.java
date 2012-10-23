package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase específica para el héroe del juego
 * @author Sergio
 *
 */
public class Heroe extends Personaje {

	/**
	 * Carga datos específicos del héroe, incluyendo su hitbox y su estado
	 * inicial
	 * @param nombre El nombre del {@link Actor}
	 * @param puntoOrigen El punto donde se va a originar el {@link Actor}
	 */
	public Heroe(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);
		
		width = 125.0f;
		height = 150.0f;
		
		hitbox = new Rectangulo(height, width / 3, true);

		estado = DETENIDO;
		destinoX = x;
		direccionX = DERECHA;
		velocidad = 500;
		
		cargarAnimaciones();
	}
	
	@Override
	protected void cargarAnimaciones() {
		try {
			animacion.put("detenido", initAnimacion("detenido"));
			animacion.put("corriendo", initAnimacion("corriendo"));
			animacion.put("colisionando", initAnimacion("colisionando"));
		} catch (NullPointerException e) {
			U.err(e);
		}
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		moverHeroe(delta);
	}
	
	
	/**
	 * Mueve al héroe al presionar las teclas adecuadas
	 * @param delta El delta de {@link Screen.render()}
	 */
	private void moverHeroe(float delta) {
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)) {
			if (estado != COLISIONANDO) estado = MOVIMIENTO;
			if (Gdx.input.isKeyPressed(Keys.A)) {
				direccionX = IZQUIERDA;
				moverIzquierda(delta);
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				direccionX = DERECHA;
				moverDerecha(delta);
			} 
		} else {
			if (estado != COLISIONANDO) estado = DETENIDO;
		}
	}
}
