package com.lumpundform.actores;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.colision.Rectangulo;

public class Jefe extends Enemigo {

	public Jefe(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);

		setWidth(171.0f);
		setHeight(132.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));
		
		setVida(200.0f);
		setVidaMax(200.0f);
		
		cargarAnimaciones("detenido");
	}

}
