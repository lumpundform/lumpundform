package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.AtaqueMisil;

public class HabilidadDisparar extends Habilidad {

	public HabilidadDisparar(Personaje actor) {
		super(actor, "disparar", 2.0f, 4.0f);

		if (Heroe.class.isInstance(actor)) {
			setCooldownDefault(0.1f);
			setCooldown(0.0f);
		}

		setMana(5.0f);
	}

	@Override
	public void ejecutar(Vector2 pos) {
		if (sePuedeEjecutar()) {
			crearAtaque(new AtaqueMisil(getActor()));
		}
	}

}
