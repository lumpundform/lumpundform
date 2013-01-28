package com.lumpundform.excepciones;

import com.lumpundform.colision.Linea;

public class LineaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 9L;

	public LineaInvalidaException(Linea l) {
		super("La línea con puntos " + l.getP1().x + ", " + l.getP1().y + " y " + l.getP2().x + ", " + l.getP2().y
				+ " es inválida.");
	}

}
