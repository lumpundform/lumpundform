package com.lumpundform.excepciones;

/**
 * Cuando se quiere leer o dibujar una animación que no existe.
 * 
 * @author Sergio Valencia
 * 
 */
public class AnimacionInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 7L;

	public AnimacionInexistenteException(String personaje, String animacion) {
		super("No existe la animción " + animacion + " para el personaje " + personaje);
	}

}
