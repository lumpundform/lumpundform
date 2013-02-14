package com.lumpundform.excepciones;

public class SpriteSheetInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 10L;

	public SpriteSheetInvalidoException(String mensaje) {
		super(mensaje);
	}

}
