package com.lumpundform.actores;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.acciones.HumanoideAction;
import com.lumpundform.colision.Hitbox;

/**
 * Humanoides del juego. Pueden tener o no tener magia.
 * 
 * @author Sergio Valencia
 * 
 */
public class Humanoide extends Personaje {

	/**
	 * Inicializa al {@link Humanoide} con todos sus datos necesarios.
	 * 
	 * @param nombre
	 *            El nombre del {@link ObjetoActor}.
	 * @param puntoOrigen
	 *            En donde se va a originar el {@link ObjetoActor}.
	 */
	public Humanoide(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);

		setWidth(125.0f);
		setHeight(150.0f);

		setHitboxDefault(new Hitbox(this, getHeight(), getWidth() / 2));

		setEstado(Estado.DETENIDO);
		setDestinoX(getX());
		setDireccionX(Direccion.DERECHA);
		setVelocidad(200);

		setDistanciaAlejamiento(200.0f);

		setVida(100.0f);
		setVidaMax(100.0f);
		setMana(100.0f);
		setManaMax(100.0f);
		
		cargarAnimaciones("detenido", "corriendo");
		cargarHabilidades("disparar", "teletransportar");

		addAction(new HumanoideAction());
	}

}
