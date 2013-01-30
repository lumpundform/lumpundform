package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.AtaqueEscudo;

/**
 * La habilidad escudo del {@link com.lumpundform.actores.Heroe}
 * 
 * @author Sergio Valencia
 * 
 */
public class HabilidadEscudo extends Habilidad {

	protected HabilidadEscudo(Personaje actor, String nombre) {
		super(actor, nombre, 0.0f);
	}

	@Override
	public void ejecutar(Vector2 pos) {
		if (sePuedeEjecutar()) {
			crearAtaque(new AtaqueEscudo(getActor()));
		}
	}

}
