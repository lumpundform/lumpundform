package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;

public abstract class Habilidad {
	protected Personaje actor;
	public String nombre;

	protected float cooldown;
	protected float cooldownDefault;

	protected float mana;

	public Habilidad(Personaje actor, String nombre, float cooldownDefault) {
		this.actor = actor;
		this.nombre = nombre;
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
