package com.lumpundform.lumpundform;

public abstract class MovimientoHeroe {
	/**
	 * Mueve al héreo hacia los lados dependiendo de la tecla que se presionó
	 * y de la velocidad actual del héroe
	 * @param tecla El código de la tecla que se presionó
	 */
	public abstract void movimientoTecla(int tecla);
}
