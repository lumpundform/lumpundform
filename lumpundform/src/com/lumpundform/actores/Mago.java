package com.lumpundform.actores;

import com.badlogic.gdx.math.Vector2;

/**
 * Todos los {@link Humanoide}s del juego que pueden utilizar magia.
 * 
 * @author Sergio Valencia
 * 
 */
public class Mago extends Humanoide {

	/**
	 * Inicializa al {@link Mago} con todos sus datos necesarios.
	 * 
	 * @param nombre
	 *            El nombre del mago
	 * @param puntoOrigen
	 *            El punto donde se va a originar el {@link ObjetoActor}
	 */
	protected Mago(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);
	}

}
