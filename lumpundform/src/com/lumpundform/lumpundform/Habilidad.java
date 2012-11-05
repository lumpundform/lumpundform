package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Habilidad extends ObjetoActor {
	protected float cooldown;
	protected float cooldownDefault;

	public Habilidad(String nombre, float cooldownDefault) {
		super(nombre);

		this.cooldown = 0.0f;
		this.cooldownDefault = cooldownDefault;
	}

	public abstract void ejecutar(Personaje actor, Vector2 pos);

	public void reducirCooldown(float delta) {
		cooldown -= delta;
		if (cooldown < 0.0f) {
			cooldown = 0.0f;
		}
		//U.ds(String.format("Cooldown teletransportar: %.2fs", cooldown));
	}

	@Override
	protected void cargarAnimaciones() {
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
	}

	@Override
	protected TextureRegion getCuadroActual() {
		return null;
	}

}
