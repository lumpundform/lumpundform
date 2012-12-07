package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.AtaqueMisil;

public class HabilidadDisparar extends Habilidad {

	public HabilidadDisparar(Personaje actor, String nombre) {
		super(actor, nombre, 0.1f);

		setMana(5.0f);
	}

	@Override
	public void ejecutar(Vector2 pos) {
		if (sePuedeEjecutar()) {
			getActor().quitarMana(getMana());
			getActor().getStage().addActor(new AtaqueMisil(getActor()));
	
			setCooldown(getCooldownDefault());
		}
	}

}
