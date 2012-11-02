package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Evento {
	private TiledObjectGroup eventosMapa;
	private CamaraJuego camara;

	public Array<Evento> eventos;

	public Array<Evento> oe;

	public Vector2 vector;
	public String nombre;
	public String tipo;
	public Boolean activado;
	public Boolean terminado;

	public Evento(TiledObjectGroup eventosMapa, CamaraJuego camara) {
		this.eventosMapa = eventosMapa;
		this.camara = camara;
		crearEventos();
	}

	private Evento(Vector2 vector, String nombre, String tipo,
			Boolean activado, Boolean terminado) {
		this.vector = vector;
		this.nombre = nombre;
		this.tipo = tipo;
		this.activado = activado;
		this.terminado = terminado;
	}

	private void crearEventos() {
		oe = new Array<Evento>();
		for (int i = 0; i < this.eventosMapa.objects.size(); i++) {
			oe.add(new Evento(U.voltearCoordenadas(camara,
					this.eventosMapa.objects.get(i).x,
					this.eventosMapa.objects.get(i).y),
					this.eventosMapa.objects.get(i).name,
					this.eventosMapa.objects.get(i).type, false, false));
		}
	}

	public Array<Evento> getEventos() {
		return oe;
	}

}