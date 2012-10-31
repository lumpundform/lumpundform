package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

/**
 * Un {@link Poligono} de tipo rectángulo
 * 
 * @author Sergio
 * 
 */
public class Rectangulo extends Poligono {
	private float alto;
	private float ancho;
	public boolean centrado;

	/**
	 * Manda llamar {@link this(float, float, boolean)} con valor predeterminado
	 * de centrado de false
	 * 
	 * @param alto
	 *            El alto del {@link Rectangulo}
	 * @param ancho
	 *            El ancho del {@link Rectangulo}
	 */
	public Rectangulo(float alto, float ancho) {
		this(alto, ancho, false);
	}

	/**
	 * Inicializa un {@link Rectangulo} del alto y ancho dados
	 * 
	 * @param alto
	 *            El alto del {@link Rectangulo}
	 * @param ancho
	 *            El ancho del {@link Rectangulo}
	 * @param centrado
	 *            Si el punto de referencia debe ser el centro del
	 *            {@link Rectangulo}
	 */
	public Rectangulo(float alto, float ancho, boolean centrado) {
		super(new Vector2[4]);
		this.alto = alto;
		this.ancho = ancho;
		this.centrado = centrado;
	}

	/**
	 * Regresa el ancho del {@link Rectangulo}
	 * 
	 * @return El ancho
	 */
	public float getAncho() {
		return ancho;
	}

	/**
	 * Posiciona al {@link Rectangulo} en el punto dado tomando en cuenta si el
	 * {@link Rectangulo} tiene punto de referencia centrado o no
	 * 
	 * @param x
	 *            La x del punto
	 * @param y
	 *            La y del punto
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

	/**
	 * Regresa el punto del {@link Rectangulo} especificado
	 * 
	 * @param nombre
	 *            El nombre del punto a buscar
	 * @return El punto
	 */
	public Vector2 punto(String nombre) {
		float xTemp = vertices[0].x;
		float yTemp = vertices[0].y;
		int indicePunto = 0;
		for (int i = 1; i < vertices.length; i++) {
			if (nombre.equals("inf-izq")) {
				if (vertices[i].x <= xTemp && vertices[i].y <= yTemp) {
					xTemp = vertices[i].x;
					yTemp = vertices[i].y;
					indicePunto = i;
				}
			} else if (nombre.equals("inf-der")) {
				if (vertices[i].x >= xTemp && vertices[i].y <= yTemp) {
					xTemp = vertices[i].x;
					yTemp = vertices[i].y;
					indicePunto = i;
				}
			} else if (nombre.equals("sup-izq")) {
				if (vertices[i].x <= xTemp && vertices[i].y >= yTemp) {
					xTemp = vertices[i].x;
					yTemp = vertices[i].y;
					indicePunto = i;
				}
			} else if (nombre.equals("sup-der")) {
				if (vertices[i].x >= xTemp && vertices[i].y >= yTemp) {
					xTemp = vertices[i].x;
					yTemp = vertices[i].y;
					indicePunto = i;
				}
			}
		}
		return vertices[indicePunto];
	}

	/**
	 * Regresa el punto del centro del {@link Rectangulo}
	 * 
	 * @return El punto
	 */
	public Vector2 centro() {
		return new Vector2((punto("inf-izq").x + punto("inf-der").x) / 2,
				(punto("inf-izq").y + punto("sup-izq").y) / 2);
	}
}
