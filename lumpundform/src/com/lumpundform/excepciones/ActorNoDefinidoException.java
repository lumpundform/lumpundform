package com.lumpundform.excepciones;

public class ActorNoDefinidoException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	public ActorNoDefinidoException(String message) {
		super(message);
	}
}
