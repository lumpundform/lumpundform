package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

/**
 * Contiene los dos puntos que forman una línea, para hacer comparaciones con
 * otros puntos
 * 
 * @author Sergio
 * 
 */
public class Linea {
	private Vector2 p1;
	private Vector2 p2;

	private float yMenor;
	private float yMayor;
	private float xMenor;
	private float xMayor;

	public Linea(Vector2 p1, Vector2 p2) {
		this.p1 = p1;
		this.p2 = p2;

		yMenor = p1.y < p2.y ? p1.y : p2.y;
		yMayor = p1.y > p2.y ? p1.y : p2.y;
		xMenor = p1.x < p2.x ? p1.x : p2.x;
		xMayor = p1.x > p2.x ? p1.x : p2.x;
	}

	/**
	 * Regresa el valor X de una {@link Linea} para una Y dada
	 * 
	 * @param punto
	 *            El punto que tiene la coordenada en Y
	 * @return El punto en X para la Y dada
	 */
	public float xEnY(Vector2 punto) {
		if (direccionLinea() == ".") {
			if (p1.x == punto.x)
				return p1.x;
			// TODO: Que escriba en un archivo si le estamos pasando un punto
			// como línea
		} else if (esRecta()) {
			if (p1.x == p2.x && punto.y >= yMenor && punto.y <= yMayor)
				return p1.x; // Líneas verticales
			if (p1.y == p2.y && punto.y == p1.y && punto.x >= xMenor
					&& punto.x <= xMayor)
				return punto.x; // Líneas horizontales
		} else if (esDiagonal()) {
			float proporcion = longitudY(punto) / longitudY();
			float longitudLineaCorta = longitudX() * proporcion;
			if (xDeYMenor() == xMenor) {
				return xDeYMenor() + longitudLineaCorta;
			} else {
				return xDeYMenor() - longitudLineaCorta;
			}
		}
		// TODO: Hacer que regrese un error, en vez de -1000
		return -1000;
	}

	/**
	 * Regresa el valor Y de una {@link Linea} para una X dada
	 * 
	 * @param punto
	 *            El punto que tiene la coordenada en X
	 * @return El punto en Y para la X dada
	 */
	public float yEnX(Vector2 punto) {
		if (direccionLinea() == ".") {
			if (p1.x == punto.x)
				return p1.y;
			// TODO: Que escriba en un archivo si le estamos pasando un punto
			// como línea
		} else if (esRecta()) {
			if (p1.y == p2.y && punto.x >= xMenor && punto.x <= xMayor)
				return p1.y; // Líneas horizontales
			if (p1.x == p2.x && punto.x == p1.y && punto.y >= yMenor
					&& punto.y <= yMayor)
				return punto.y; // Líneas verticales
		} else if (esDiagonal()) {
			float proporcion = longitudX(punto) / longitudX();
			float longitudLineaCorta = longitudY() * proporcion;
			if (yDeXMenor() == yMenor) {
				return yDeXMenor() + longitudLineaCorta;
			} else {
				return yDeXMenor() - longitudLineaCorta;
			}
		}
		// TODO: Hacer que regrese un error en vez de -1000
		return -1000;
	}

	/**
	 * Si la diagonal va hacia arriba o hacia abajo
	 * 
	 * @return La dirección de la diagonal de la {@link Linea}
	 */
	public String direccionDiagonal() {
		if (direccionLinea() == "xy" || direccionLinea() == "-x-y")
			return "arriba";
		else
			return "abajo";
	}

	/**
	 * Regresa la paneidnete de la {@link Linea}
	 * 
	 * @return El valor de la pendiente
	 */
	public Float pendiente() {
		if (longitudX() == 0)
			return null;
		return longitudY() / longitudX();
	}

	/**
	 * Cuanto mide la {@link Linea} horizontalmente
	 * 
	 * @return La medida
	 */
	private float longitudX() {
		return xMayor - xMenor;
	}

	/**
	 * Cuanto mide la {@link Linea} horizontalmente desde el comienzo hasta un
	 * punto dado
	 * 
	 * @param punto
	 *            El punto para sacar la distancia
	 * @return La medida
	 */
	private float longitudX(Vector2 punto) {
		return punto.x - xMenor;
	}

	/**
	 * Cuanto mide la {@link Linea} verticalmente
	 * 
	 * @return La medida
	 */
	private float longitudY() {
		return yMayor - yMenor;
	}

	/**
	 * Cuanto mide la {@link Linea} verticalmente desde el comienzo hasta un
	 * punto dado
	 * 
	 * @param punto
	 *            El punto para sacar la distancia
	 * @return La medida
	 */
	private float longitudY(Vector2 punto) {
		return punto.y - yMenor;
	}

	/**
	 * Regresa x menor o mayor
	 * 
	 * @param nombre
	 *            "menor" o "mayor"
	 * @return El valor de la x
	 */
	public float getX(String nombre) {
		return nombre == "mayor" ? xMayor : xMenor;
	}

	/**
	 * Regresa y menor o mayor
	 * 
	 * @param nombre
	 *            "menor" o "mayor"
	 * @return El valor de la y
	 */
	public float getY(String nombre) {
		return nombre == "mayor" ? yMayor : yMenor;
	}

	/**
	 * El valor Y del punto con la X menor
	 * 
	 * @return El valor Y
	 */
	private float yDeXMenor() {
		if (xMenor == p1.x)
			return p1.y;
		else
			return p2.y;
	}

	/**
	 * El valor X del punto con la Y menor
	 * 
	 * @return El valor Y
	 */
	private float xDeYMenor() {
		if (yMenor == p1.y)
			return p1.x;
		else
			return p2.x;
	}

	/**
	 * Dice si la {@link Linea} es una recta
	 * 
	 * @return {@link true} si es recta, {@link false} si no
	 */
	private boolean esRecta() {
		String direccionLinea = direccionLinea();
		return direccionLinea == "x" || direccionLinea == "-x"
				|| direccionLinea == "y" || direccionLinea == "-y";
	}

	/**
	 * Dice si la {@link Linea} es horizontal
	 * 
	 * @return {@link true} si es horizontal, {@link false} si no
	 */
	public boolean esHorizontal() {
		String direccionLinea = direccionLinea();
		return direccionLinea == "x" || direccionLinea == "-x";
	}

	/**
	 * Dice si la {@link Linea} es vertical
	 * 
	 * @return {@link true} si es vertical, {@link false} si no
	 */
	public boolean esVertical() {
		String direccionLinea = direccionLinea();
		return direccionLinea == "y" || direccionLinea == "-y";
	}

	/**
	 * Dice si la {@link Linea} es diagonal
	 * 
	 * @return {@link true} si es diagonal, {@link false} si no
	 */
	private boolean esDiagonal() {
		String direccionLinea = direccionLinea();
		if (direccionLinea == "xy" || direccionLinea == "-xy"
				|| direccionLinea == "x-y" || direccionLinea == "-x-y") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Regresa la dirección de la {@link Linea} desde el primer punto hacia el
	 * segundo punto. Regresa "." si es un punto, "x" y "-x" si es línea
	 * horizontal hacia la derecha o izquierda respectivamente, "y" y "-y" si es
	 * línea vertical hacia arriba o abajo respectivamente, "xy" si es diagonal
	 * hacia arriba a la derecha, "-xy" si es diagonal hacia arriba a la
	 * izquierda, "-x-y" si es diagonal hacia abajo a la izquierda y "x-y" si es
	 * diagonal hacia abajo a la derecha.
	 * 
	 * @return El nombre de la dirección
	 */
	public String direccionLinea() {
		String nombre = "";

		// Punto
		if (p1.x == p2.x && p1.y == p2.y) {
			nombre = ".";

			// Rectas
		} else if (p1.y == p2.y && p1.x < p2.x) {
			nombre = "x";
		} else if (p1.y == p2.y && p1.x > p2.x) {
			nombre = "-x";
		} else if (p1.x == p2.x && p1.y < p2.y) {
			nombre = "y";
		} else if (p1.x == p2.x && p1.y > p2.y) {
			nombre = "-y";

			// Diagonales
		} else if (p1.y > p2.y && p1.x < p2.x) {
			nombre = "x-y";
		} else if (p1.y > p2.y && p1.x > p2.x) {
			nombre = "-x-y";
		} else if (p1.y < p2.y && p1.x < p2.x) {
			nombre = "xy";
		} else if (p1.y < p2.y && p1.x > p2.x) {
			nombre = "-xy";
		}

		return nombre;
	}
}
