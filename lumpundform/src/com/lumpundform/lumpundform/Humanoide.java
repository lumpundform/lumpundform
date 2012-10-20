package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class Humanoide extends Personaje {

	protected Humanoide(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);
		
		width = 125.0f;
		height = 150.0f;
		
		hitbox = new Rectangulo(height, width / 2, true);
		
		estado = MOVIMIENTO;
		destinoX = x;
		direccionX = DERECHA;
		velocidad = 200;
		
		cargarAnimaciones();
	}
	protected void cargarAnimaciones() {
		try {
			animacion.put("detenido", initAnimacion("detenido"));
			animacion.put("corriendo", initAnimacion("corriendo"));
		} catch (NullPointerException e) {
			U.err(e);
		}
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (x < 50) {
			direccionX = DERECHA;
		} else if (x > 400) {
			direccionX = IZQUIERDA;
		}
		
		if (direccionX == IZQUIERDA) {
			moverIzquierda(delta);
		} else if (direccionX == DERECHA) {
			moverDerecha(delta);
		}
	}

}
