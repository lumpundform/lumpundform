package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

/**
 * Define un poligono basado en los vértices dados
 * @author Sergio
 *
 */
public class Poligono {
	public Vector2[] vertices;
	
	public Poligono(Vector2[] vertices) {
		this.vertices = vertices;
	}

	/**
	 * Regresa si existe una colisión del poligono con el punto dado
	 * @param punto El punto a revisar para la colisión
	 * @return true si hay colisión, false si no
	 */
	public boolean estaColisionando(Vector2 punto) {
		Linea izq = null;
		Linea der = null;
		Linea arr = null;
		Linea ab = null;
		
		// Recorre todas las líneas del polígono
		for(int i = 0; i < vertices.length; i++) {
			int i2 = (i + 1 >= vertices.length) ? 0 : i + 1;
			Vector2 p1 = vertices[i];
			Vector2 p2 = vertices[i2];
			Linea lin = new Linea(p1, p2);
			if (lin.xMenor <= punto.x && lin.xMayor >= punto.x && lin.yEnX(punto) >= punto.y) {
				if (arr == null) arr = lin;
			}
			if (lin.xMenor <= punto.x && lin.xMayor >= punto.x && lin.yEnX(punto) <= punto.y) {
				if (ab == null) ab = lin;
			}
			if (lin.yMenor <= punto.y && lin.yMayor >= punto.y && lin.xEnY(punto) >= punto.x) {
				if (der == null) der = lin;
			}
			if (lin.yMenor <= punto.y && lin.yMayor >= punto.y && lin.xEnY(punto) <= punto.x) {
				if (izq == null) izq = lin;
			}
		}
		
		return !(arr == null || ab == null || der == null || izq == null);
	}
	
	/**
	 * Calcula si está colisionando con otro polígono
	 * @param poligono El poligono con el cual se quiere revisar si está
	 * 				   colisionando
	 * @return {@link true} si sí está colisionando, {@link false} si no
	 */
	public boolean estaColisionando(Poligono poligono) {
		// TODO: Hacer que la colisión no funcione únicamente con rectángulos
		for (int i = 0; i < poligono.vertices.length; i++) {
			if (estaColisionando(poligono.vertices[i])) {
				return true;
			}
		}
		return false;
	}
}
