package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class Linea {
	private Vector2 p1;
	private Vector2 p2;
	
	public float yMenor;
	public float yMayor;
	public float xMenor;
	public float xMayor;

	public Linea(Vector2 p1, Vector2 p2) {
		this.p1 = p1;
		this.p2 = p2;

		yMenor = p1.y < p2.y ? p1.y : p2.y;
		yMayor = p1.y > p2.y ? p1.y : p2.y;
		xMenor = p1.x < p2.x ? p1.x : p2.x;
		xMayor = p1.x > p2.x ? p1.x : p2.x;
	}
	
	public float xEnY(Vector2 punto) {
		if (p1.x == p2.x && punto.y > yMenor && punto.y < yMayor) return p1.x; // Líneas verticales
		if (p1.y == p2.y && punto.y == p1.y && punto.x > xMenor && punto.x < xMayor) return punto.x; // Líneas horizontales
		// TODO: implementar líneas diagonales
		return -1000;
	}
	
	public float yEnX(Vector2 punto) {
		if (p1.y == p2.y && punto.x > xMenor && punto.x < xMayor) return p1.y; // Líneas horizontales
		if (p1.x == p2.x && punto.x == p1.y && punto.y > yMenor && punto.y < yMayor) return punto.y; // Líneas verticales
		// TODO: implementar líneas diagonales
		return -1000;
	}
}

