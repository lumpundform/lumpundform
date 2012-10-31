package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

/**
 * Define un poligono basado en los vértices dados
 * 
 * @author Sergio
 * 
 */
public class Poligono {
	public Vector2[] vertices;

	public Poligono(Vector2[] vertices) {
		this.vertices = vertices;
	}

	/**
	 * Regresa si existe una colisión del {@link Poligono} con el punto dado
	 * 
	 * @param punto
	 *            El punto a revisar para la colisión
	 * @return {@link true} si hay colisión, {@link false} si no
	 */
	public boolean estaColisionando(Vector2 punto) {
		Linea izq = null;
		Linea der = null;
		Linea arr = null;
		Linea ab = null;

		Linea[] lineas = lineasPoligono(punto);
		arr = lineas[0];
		ab = lineas[1];
		izq = lineas[2];
		der = lineas[3];

		return !(arr == null || ab == null || der == null || izq == null);
	}

	/**
	 * Calcula si está colisionando con otro {@link Poligono}
	 * 
	 * @param poligono
	 *            El {@link Poligono} con el cual se quiere revisar si está
	 *            colisionando
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

	/**
	 * Regresa la {@link Linea} de la posición dada para el {@link Poligono} con
	 * el punto dado
	 * 
	 * @param nombre
	 *            El nombre de la posición de la {@link Linea}
	 * @param punto
	 *            El punto a revisar
	 * @return La {@link Linea} o {@link null} si no existe
	 */
	public Linea linea(String nombre, Vector2 punto) {
		Linea[] lineas = lineasPoligono(punto);
		if (nombre.equals("derecha")) {
			return lineas[3];
		} else if (nombre.equals("abajo")) {
			return lineas[1];
		} else if (nombre.equals("izquierda")) {
			return lineas[2];
		} else {
			return lineas[0];
		}
	}

	/**
	 * [0] = arriba [1] = abajo [2] = izquierda [3] = derecha
	 * 
	 * @param punto
	 *            El punto para revisar las {@link Linea}s
	 * @return Un arreglo de {@link Linea}s
	 */
	private Linea[] lineasPoligono(Vector2 punto) {
		Linea[] lineas = { null, null, null, null };
		// Recorre todas las líneas del polígono
		for (int i = 0; i < vertices.length; i++) {
			int i2 = (i + 1 >= vertices.length) ? 0 : i + 1;
			Vector2 p1 = vertices[i];
			Vector2 p2 = vertices[i2];
			Linea lin = new Linea(p1, p2);
			if (lin.xMenor <= punto.x && lin.xMayor >= punto.x
					&& lin.yEnX(punto) >= punto.y) {
				if (lineas[0] == null)
					lineas[0] = lin;
			}
			if (lin.xMenor <= punto.x && lin.xMayor >= punto.x
					&& lin.yEnX(punto) <= punto.y) {
				if (lineas[1] == null)
					lineas[1] = lin;
			}
			if (lin.yMenor <= punto.y && lin.yMayor >= punto.y
					&& lin.xEnY(punto) >= punto.x) {
				if (lineas[3] == null)
					lineas[3] = lin;
			}
			if (lin.yMenor <= punto.y && lin.yMayor >= punto.y
					&& lin.xEnY(punto) <= punto.x) {
				if (lineas[2] == null)
					lineas[2] = lin;
			}
		}

		return lineas;
	}
}
