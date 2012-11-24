package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class HabilidadDisparar extends Habilidad {

	public HabilidadDisparar(String nombre) {
		super(0.1f);

		mana = 5.0f;
	}

	@Override
	public void ejecutar(Personaje actor, Vector2 pos) {
		if (cooldown == 0.0f) {
			actor.quitarMana(mana);
			actor.getStage().addActor(new AtaqueMisil(actor));

			cooldown = cooldownDefault;
		}
	}

}
