package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class Evento {
	public Vector2 vector;
	public String nombre;
	public String tipo;
	public Boolean activado;
	public Boolean terminado;

	public Evento(Vector2 vector, String nombre, String tipo,
			Boolean activado, Boolean terminado) {
		this.vector = vector;
		this.nombre = nombre;
		this.tipo = tipo;
		this.activado = activado;
		this.terminado = terminado;
	}

}