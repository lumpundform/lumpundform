package com.lumpundform.actores;

import com.badlogic.gdx.math.Vector2;

public class Enemigo extends Personaje {

	public Enemigo(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);
		setEnemigo(true);
	}

}
