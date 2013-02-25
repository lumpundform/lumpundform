package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.AtaqueBlizzard;

/**
 * La habilidad para el ataque blizzard
 * 
 * @author Sergio Valencia
 * 
 */
public class HabilidadBlizzard extends HabilidadSostenida {

	protected HabilidadBlizzard(Personaje actor) {
		super(actor, "blizzard", 0.0f, 15.0f);
		setMana(10.0f);
	}

	@Override
	public void ejecutar(Vector2 pos) {
		ataque = new AtaqueBlizzard(getActor(), this);
		super.ejecutar(pos);
	}

}
