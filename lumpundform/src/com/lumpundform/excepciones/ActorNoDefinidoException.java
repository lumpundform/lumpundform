package com.lumpundform.excepciones;

/**
 * Cuando se quiere agregar un actor que no se haya definido antes.
 * 
 * @author Sergio Valencia
 * 
 */
public class ActorNoDefinidoException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	public ActorNoDefinidoException(String message) {
		super(message);
	}
}
