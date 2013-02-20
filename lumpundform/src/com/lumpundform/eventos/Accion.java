package com.lumpundform.eventos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Accion {

	private boolean terminado = false;

	private String nombreActor;
	private String objetivo;
	private String destino;
	private String texto;
	private String posicionTexto;

	private float posicionX = 0.0f;
	private float posicionY = 0.0f;

	public Accion(Element accion) {
		this.nombreActor = accion.get("actor");
		this.objetivo = accion.get("objetivo");

		if (accion.getAttributes().containsKey("destino"))
			this.destino = accion.get("destino");
		if (accion.getAttributes().containsKey("texto"))
			this.texto = accion.get("texto");
		if(accion.getAttributes().containsKey("posicionTexto"))
			this.posicionTexto = accion.get("posicionTexto");
		if (accion.getAttributes().containsKey("posicionX"))
			this.posicionX = Float.parseFloat(accion.get("posicionX"));
		if (accion.getAttributes().containsKey("posicionY"))
			this.posicionY = Float.parseFloat(accion.get("posicionY"));
	}

	public String getNombreActor() {
		return nombreActor;
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

	public Vector2 getPosicion() {
		return new Vector2(posicionX, posicionY);
	}

	public boolean getTerminado() {
		return terminado;
	}
	
	public String getPosicionTexto() {
		return posicionTexto;
	}

	public void terminar() {
		terminado = true;
	}
}