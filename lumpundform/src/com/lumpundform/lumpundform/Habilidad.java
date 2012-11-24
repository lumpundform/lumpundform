package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public abstract class Habilidad {
	protected float cooldown;
	protected float cooldownDefault;
	
	protected float mana;

	public Habilidad(float cooldownDefault) {
		this.cooldown = 0.0f;
		this.cooldownDefault = cooldownDefault;
	}

	public abstract void ejecutar(Personaje actor, Vector2 pos);

	public void reducirCooldown(float delta) {
		cooldown -= delta;
		if (cooldown < 0.0f) {
			cooldown = 0.0f;
		}
	}

}
