package com.lumpundform.excepciones;

public class HabilidadInexistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	public HabilidadInexistenteException(String message) {
		super(message);
	}

}