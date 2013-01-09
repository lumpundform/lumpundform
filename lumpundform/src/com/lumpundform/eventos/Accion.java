package com.lumpundform.eventos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Accion {
	public Boolean activado = false;
	public Boolean terminado = false;
	public String actor;
	public String objetivo;
	public String destino;
	public String texto;
	public float x;
	public float y;
	

	public Accion(Element accion) {
		this.actor = accion.get("actor");
		this.objetivo = accion.get("objetivo");

		if (accion.getAttributes().containsKey("destino"))
			this.destino = accion.get("destino");
		if (accion.getAttributes().containsKey("texto"))
			this.texto = accion.get("texto");
		if (accion.getAttributes().containsKey("x"))
			this.x = Float.parseFloat(accion.get("x"));
		if (accion.getAttributes().containsKey("y"))
			this.y = Float.parseFloat(accion.get("y"));
	}

	public String getObjetivo() {
		return objetivo;
	}

	public String getTexto() {
		return texto;
	}

	public float getDestino() {
		return Float.parseFloat(destino);
	}
	
	public Vector2 getPosicionVector() {
		return new Vector2(x,y);
	}
}