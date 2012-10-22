package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

/**
 * Un {@link Poligono} de tipo rectángulo
 * @author Sergio
 *
 */
public class Rectangulo extends Poligono {
	private float alto;
	private float ancho;
	private boolean centrado;
	
	/**
	 * Manda llamar {@link Rectangulo(float, float, boolean)} con valor
	 * predeterminado de centrado de false
	 * @param alto El alto del {@link Rectangulo}
	 * @param ancho El ancho del {@link Rectangulo}
	 */
	public Rectangulo(float alto, float ancho) {
		this(alto, ancho, false);
	}
	/**
	 * Inicializa un {@link Rectangulo} del alto y ancho dados
	 * @param alto El alto del {@link Rectangulo}
	 * @param ancho El ancho del {@link Rectangulo}
	 * @param centrado Si el punto de referencia debe ser el centro del
	 *                 {@link Rectangulo}
	 */
	public Rectangulo(float alto, float ancho, boolean centrado) {
		super(new Vector2[4]);
		this.alto = alto;
		this.ancho = ancho;
		this.centrado = centrado;
	}
	
	/**
	 * Posiciona al {@link Rectangulo} en el punto dado tomando en cuenta si
	 * el {@link Rectangulo} tiene punto de referencia centrado o no
	 * @param x La x del punto
	 * @param y La y del punto
	 * @return El {@link Rectangulo} en la posición dada
	 */
	public Rectangulo posicionar(float x, float y) {
		if (centrado) {
			vertices[0] = new Vector2(x - (ancho / 2), y - (alto / 2));
			vertices[1] = new Vector2(x - (ancho / 2), y + (alto / 2));
			vertices[2] = new Vector2(x + (ancho / 2), y + (alto / 2));
			vertices[3] = new Vector2(x + (ancho / 2), y - (alto / 2));
		} else {
			vertices[0] = new Vector2(x, y);
			vertices[1] = new Vector2(x, y + alto);
			vertices[2] = new Vector2(x + ancho, y + alto);
			vertices[3] = new Vector2(x + ancho, y);
		}
		return this;
	}
}
