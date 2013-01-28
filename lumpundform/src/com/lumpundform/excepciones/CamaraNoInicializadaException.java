package com.lumpundform.excepciones;

public class CamaraNoInicializadaException extends RuntimeException {

	private static final long serialVersionUID = 8L;

	public CamaraNoInicializadaException() {
		super("No se ha inicializado la cámara en la función U. Mandar llamar U.init().");
	}

}
