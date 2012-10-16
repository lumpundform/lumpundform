package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class Rectangulo extends Poligono {
	private float alto;
	private float ancho;
	private boolean centrado;
	
	public Rectangulo(float alto, float ancho) {
		this(alto, ancho, true);
	}
	public Rectangulo(float alto, float ancho, boolean centrado) {
		super(new Vector2[4]);
		this.alto = alto;
		this.ancho = ancho;
		this.centrado = centrado;
	}
	
	public Rectangulo posicionar(float x, float y) {
		if (centrado) {
			puntos[0] = new Vector2(x - (ancho / 2), y - (alto / 2));
			puntos[1] = new Vector2(x - (ancho / 2), y + (alto / 2));
			puntos[2] = new Vector2(x + (ancho / 2), y + (alto / 2));
			puntos[3] = new Vector2(x + (ancho / 2), y - (alto / 2));
		} else {
			puntos[0] = new Vector2(x, y);
			puntos[1] = new Vector2(x, y + alto);
			puntos[2] = new Vector2(x + ancho, y + alto);
			puntos[3] = new Vector2(x + ancho, y);
		}
		return this;
	}
}
