package com.lumpundform.colision;

import com.badlogic.gdx.math.Vector2;

/**
 * Contiene los dos puntos que forman una línea, para hacer comparaciones con
 * otros puntos. Se utiliza únicamente para calcular la colisión de
 * {@link Poligono}s.
 * 
 * @author Sergio Valencia
 * 
 */
public class Linea {
	private Vector2 p1;
	private Vector2 p2;

	private float yMenor;
	private float yMayor;
	private float xMenor;
	private float xMayor;

	/**
	 * Inicializa la {@link Linea} con los dos puntos especificados.
	 * 
	 * @param p1
	 *            El primer punto.
	 * @param p2
	 *            El segundo putno.
	 */
	Linea(Vector2 p1, Vector2 p2) {
		this.setP1(p1);
		this.setP2(p2);

		yMenor = p1.y < p2.y ? p1.y : p2.y;
		yMayor = p1.y > p2.y ? p1.y : p2.y;
		xMenor = p1.x < p2.x ? p1.x : p2.x;
		xMayor = p1.x > p2.x ? p1.x : p2.x;
	}

	/**
	 * Regresa el valor X de una {@link Linea} para una Y dada.
	 * 
	 * @param punto
	 *            El punto que tiene la coordenada en Y.
	 * @return El punto en X para la Y dada.
	 */
	public float xEnY(Vector2 punto) {
		if (direccionLinea() == ".") {
			if (getP1().x == punto.x)
				return getP1().x;
			// TODO: Que escriba en un archivo si le estamos pasando un punto
			// como línea
		} else if (esRecta()) {
			if (getP1().x == getP2().x && punto.y >= yMenor && punto.y <= yMayor)
				return getP1().x; // Líneas verticales
			if (getP1().y == getP2().y && punto.y == getP1().y && punto.x >= xMenor && punto.x <= xMayor)
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
		return -1000;
	}

	/**
	 * Regresa el valor Y de una {@link Linea} para una X dada.
	 * 
	 * @param punto
	 *            El punto que tiene la coordenada en X.
	 * @return El punto en Y para la X dada.
	 */
	public float yEnX(Vector2 punto) {
		if (direccionLinea() == ".") {
			if (getP1().x == punto.x)
				return getP1().y;
			// TODO: Que escriba en un archivo si le estamos pasando un punto
			// como línea
		} else if (esRecta()) {
			if (getP1().y == getP2().y && punto.x >= xMenor && punto.x <= xMayor)
				return getP1().y; // Líneas horizontales
			if (getP1().x == getP2().x && punto.x == getP1().y && punto.y >= yMenor && punto.y <= yMayor)
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
		return -1000;
	}

	/**
	 * Si la diagonal va hacia arriba o hacia abajo.
	 * 
	 * @return <code>"arriba"</code> o <code>"abajo"</code>.
	 */
	public String direccionDiagonal() {
		if (direccionLinea() == "xy" || direccionLinea() == "-x-y")
			return "arriba";
		else
			return "abajo";
	}

	/**
	 * Regresa la paneidnete de la {@link Linea}.
	 * 
	 * @return El valor de la pendiente.
	 */
	public Float pendiente() {
		if (longitudX() == 0)
			return null;
		return longitudY() / longitudX();
	}

	/**
	 * @return La medida de la {@link Linea} horizontalmente.
	 */
	private float longitudX() {
		return xMayor - xMenor;
	}

	/**
	 * Cuanto mide la {@link Linea} horizontalmente desde el comienzo hasta un
	 * punto dado.
	 * 
	 * @param punto
	 *            El punto para sacar la distancia.
	 * @return La medida.
	 */
	private float longitudX(Vector2 punto) {
		return punto.x - xMenor;
	}

	/**
	 * @return Cuanto mide la {@link Linea} verticalmente.
	 */
	private float longitudY() {
		return yMayor - yMenor;
	}

	/**
	 * Cuanto mide la {@link Linea} verticalmente desde el comienzo hasta un
	 * punto dado.
	 * 
	 * @param punto
	 *            El punto para sacar la distancia.
	 * @return La medida.
	 */
	private float longitudY(Vector2 punto) {
		return punto.y - yMenor;
	}

	/**
	 * Regresa x menor o mayor.
	 * 
	 * @param nombre
	 *            <code>"menor"</code> ó <code>"mayor"</code>.
	 * @return El valor de la x.
	 */
	float getX(String nombre) {
		return nombre == "mayor" ? xMayor : xMenor;
	}

	/**
	 * Regresa y menor o mayor.
	 * 
	 * @param nombre
	 *            <code>"menor"</code> o <code>"mayor"</code>.
	 * @return El valor de la y.
	 */
	float getY(String nombre) {
		return nombre == "mayor" ? yMayor : yMenor;
	}

	/**
	 * El valor Y del punto con la X menor.
	 * 
	 * @return El valor Y.
	 */
	private float yDeXMenor() {
		if (xMenor == getP1().x)
			return getP1().y;
		else
			return getP2().y;
	}

	/**
	 * El valor X del punto con la Y menor.
	 * 
	 * @return El valor Y.
	 */
	private float xDeYMenor() {
		if (yMenor == getP1().y)
			return getP1().x;
		else
			return getP2().x;
	}

	/**
	 * @return Si la {@link Linea} es recta.
	 */
	private boolean esRecta() {
		return esHorizontal() || esVertical();
	}

	/**
	 * @return Si la {@link Linea} es horizontal.
	 */
	public boolean esHorizontal() {
		String direccionLinea = direccionLinea();
		return direccionLinea == "x" || direccionLinea == "-x";
	}

	/**
	 * @return Si la {@link Linea} es vertical.
	 */
	public boolean esVertical() {
		String direccionLinea = direccionLinea();
		return direccionLinea == "y" || direccionLinea == "-y";
	}

	/**
	 * @return Si la {@link Linea} es diagonal.
	 */
	private boolean esDiagonal() {
		String direccionLinea = direccionLinea();
		if (direccionLinea == "xy" || direccionLinea == "-xy" || direccionLinea == "x-y" || direccionLinea == "-x-y") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Regresa la dirección de la {@link Linea} desde el primer punto hacia el
	 * segundo punto.
	 * 
	 * @return <code>"."</code> si es un punto, <code>"x"</code> y
	 *         <code>"-x"</code> si es línea horizontal hacia la derecha o
	 *         izquierda respectivamente, <code>"y"</code> y <code>"-y"</code>
	 *         si es línea vertical hacia arriba o abajo respectivamente,
	 *         <code>"xy"</code> si es diagonal hacia arriba a la derecha,
	 *         <code>"-xy"</code> si es diagonal hacia arriba a la izquierda,
	 *         <code>"-x-y"</code> si es diagonal hacia abajo a la izquierda y
	 *         <code>"x-y"</code> si es diagonal hacia abajo a la derecha.
	 */
	public String direccionLinea() {
		String nombre = "";

		// Punto
		if (getP1().x == getP2().x && getP1().y == getP2().y) {
			nombre = ".";

			// Rectas
		} else if (getP1().y == getP2().y && getP1().x < getP2().x) {
			nombre = "x";
		} else if (getP1().y == getP2().y && getP1().x > getP2().x) {
			nombre = "-x";
		} else if (getP1().x == getP2().x && getP1().y < getP2().y) {
			nombre = "y";
		} else if (getP1().x == getP2().x && getP1().y > getP2().y) {
			nombre = "-y";

			// Diagonales
		} else if (getP1().y > getP2().y && getP1().x < getP2().x) {
			nombre = "x-y";
		} else if (getP1().y > getP2().y && getP1().x > getP2().x) {
			nombre = "-x-y";
		} else if (getP1().y < getP2().y && getP1().x < getP2().x) {
			nombre = "xy";
		} else if (getP1().y < getP2().y && getP1().x > getP2().x) {
			nombre = "-xy";
		}

		return nombre;
	}

	public Vector2 getP1() {
		return p1;
	}

	public void setP1(Vector2 p1) {
		this.p1 = p1;
	}

	public Vector2 getP2() {
		return p2;
	}

	public void setP2(Vector2 p2) {
		this.p2 = p2;
	}
}
