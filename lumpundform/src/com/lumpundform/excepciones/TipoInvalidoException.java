package com.lumpundform.excepciones;

public class TipoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 5L;

	public TipoInvalidoException(String tipo) {
		super("El tipo " + tipo + " no existe. Tiene que ser vida o mana.");
	}

}
