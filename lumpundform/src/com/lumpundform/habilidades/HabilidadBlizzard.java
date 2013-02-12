package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;

public class HabilidadBlizzard extends HabilidadSostenida {

	protected HabilidadBlizzard(Personaje actor) {
		super(actor, "blizzard", 0.0f, 15.0f);
		setMana(35.0f);
	}

	@Override
	public void ejecutar(Vector2 pos) {
	}

}
