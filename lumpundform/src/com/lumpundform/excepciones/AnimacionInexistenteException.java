package com.lumpundform.excepciones;

public class AnimacionInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 7L;

	public AnimacionInexistenteException(String personaje, String animacion) {
		super("No existe la animción " + animacion + " para el personaje " + personaje);
	}

}
