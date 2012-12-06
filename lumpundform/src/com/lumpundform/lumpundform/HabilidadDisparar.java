package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class HabilidadDisparar extends Habilidad {

	public HabilidadDisparar(Personaje actor, String nombre) {
		super(actor, nombre, 0.1f);

		mana = 5.0f;
	}

	@Override
	public void ejecutar(Vector2 pos) {
		super.ejecutar(pos);

		actor.quitarMana(mana);
		actor.getStage().addActor(new AtaqueMisil(actor));

		cooldown = cooldownDefault;
	}

}
