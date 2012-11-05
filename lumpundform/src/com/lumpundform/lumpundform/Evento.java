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
	
	public static void revisarEvento(Evento evento, Heroe heroe) {
		if(evento.tipo.equals("spawn") && heroe.x > (evento.vector.x - 50.0f) && heroe.x < (evento.vector.x + 50.0f)) {
			U.ds(evento.tipo);
		} else if (evento.tipo.equals("fin") && heroe.x > (evento.vector.x - 20.0f)) {
			U.ds(evento.tipo);
		}
		
	}
	
}