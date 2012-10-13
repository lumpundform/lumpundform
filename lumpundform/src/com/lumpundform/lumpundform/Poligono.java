package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class Poligono {
	public Vector2[] puntos;
	
	public Poligono(Vector2[] puntos) {
		this.puntos = puntos;
	}

	// TODO: Hacer que la función regresee boolean
	/**
	 * Regresa si existe una colisión del poligono con el punto dado
	 * @param punto El punto a revisar para la colisión
	 * @return true si hay colisión, false si no
	 */
	public String estaColisionando(Vector2 punto) {
		Linea izq = null;
		Linea der = null;
		Linea arr = null;
		Linea ab = null;
		
		// Recorre todas las líneas del polígono
		for(int i = 0; i < puntos.length; i++) {
			int i2 = (i + 1 >= puntos.length) ? 0 : i + 1;
			Vector2 p1 = puntos[i];
			Vector2 p2 = puntos[i2];
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
		
		if (arr == null || ab == null || der == null || izq == null) return "";
		else return arr.direccionDiagonal();
	}
	
	public boolean estaColisionando(Poligono poligono) {
		// TODO: implementar colisión de dos polígonos
		return false;
	}
}
