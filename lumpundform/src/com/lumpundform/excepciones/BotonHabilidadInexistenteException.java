package com.lumpundform.excepciones;

/**
 * Cuando se quiere usar una habilidad con las teclas 1-6 de un botón que no
 * existe.
 * 
 * @author Sergio Valencia
 * 
 */
public class BotonHabilidadInexistenteException extends Exception {

	private static final long serialVersionUID = 6L;

	public BotonHabilidadInexistenteException(String string) {
		super(string);
	}

}
