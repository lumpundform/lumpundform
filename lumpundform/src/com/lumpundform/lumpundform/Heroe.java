package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class Heroe extends Personaje {

	public Heroe(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);
		
		width = 125.0f;
		height = 150.0f;

		estado = DETENIDO;
		destinoX = x;
		direccionX = DERECHA;
		velocidad = 500;
		
		cargarAnimaciones();
	}
}
