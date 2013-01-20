package com.lumpundform.excepciones;

public class DatoInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 3L;

	public DatoInexistenteException(String message) {
		super(message);
	}

}
