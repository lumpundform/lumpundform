package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public abstract class Habilidad {
	protected Personaje actor;

	protected float cooldown;
	protected float cooldownDefault;

	protected float mana;

	public Habilidad(Personaje actor, float cooldownDefault) {
		this.actor = actor;
		this.cooldown = 0.0f;
		this.cooldownDefault = cooldownDefault;
	}

	public void ejecutar(Vector2 pos) {
		if (!sePuedeEjecutar())
			return;
	}

	public void reducirCooldown(float delta) {
		cooldown -= delta;
		if (cooldown < 0.0f) {
			cooldown = 0.0f;
		}
	}

	public boolean sePuedeEjecutar() {
		if (cooldown > 0.0f)
			return false;
		if (mana > actor.mana)
			return false;

		return true;
	}

}
