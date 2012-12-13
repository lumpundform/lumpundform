package com.lumpundform.actores;

import com.badlogic.gdx.math.Vector2;

public class Mago extends Personaje {

	private Mago(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);
	}
	
	@Override
	protected void cargarHabilidades() {}

}
