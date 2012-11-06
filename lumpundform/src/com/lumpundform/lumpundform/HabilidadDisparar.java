package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class HabilidadDisparar extends Habilidad {

	public HabilidadDisparar(String nombre) {
		super(0.3f);
	}

	@Override
	public void ejecutar(Personaje actor, Vector2 pos) {
		actor.getStage().addActor(new AtaqueMisil(actor));
	}

}
