package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.Ataque;

public abstract class Habilidad {
	private Personaje actor;
	private String nombre;

	private float cooldown;
	private float cooldownDefault;

	private float mana;
	
	protected Habilidad(Personaje actor, String nombre, float cooldownDefault) {
		this(actor, nombre, 0.0f, cooldownDefault);
	}

	protected Habilidad(Personaje actor, String nombre, float coolDown, float cooldownDefault) {
		this.setActor(actor);
		this.setNombre(nombre);
		this.setCooldown(coolDown);
		this.setCooldownDefault(cooldownDefault);
	}

	public abstract void ejecutar(Vector2 pos);
	
	protected void crearAtaque(Ataque ataque) {
		getActor().quitarMana(getMana());
		getActor().getStage().addActor(ataque);

		setCooldown(getCooldownDefault());
	}

	public void reducirCooldown(float delta) {
		setCooldown(getCooldown() - delta);
		if (getCooldown() < 0.0f) {
			setCooldown(0.0f);
		}
	}

	public boolean sePuedeEjecutar() {
		if (getCooldown() > 0.0f)
			return false;
		if (getMana() > getActor().getMana())
			return false;

		return true;
	}

	public float getMana() {
		return mana;
	}

	public void setMana(float mana) {
		this.mana = mana;
	}

	public Personaje getActor() {
		return actor;
	}

	public void setActor(Personaje actor) {
		this.actor = actor;
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}

	public float getCooldownDefault() {
		return cooldownDefault;
	}

	public void setCooldownDefault(float cooldownDefault) {
		this.cooldownDefault = cooldownDefault;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
