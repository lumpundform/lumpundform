package com.lumpundform.colision;

import com.lumpundform.actores.ObjetoActor;

public class Hitbox extends Rectangulo {
	// Actor
	private ObjetoActor actor;

	// Offset
	private float offsetX;
	private float offsetY;

	public Hitbox(ObjetoActor actor, float alto, float ancho) {
		this(actor, alto, ancho, (actor.getWidth() / 2),
				(actor.getHeight() / 2));
	}

	public Hitbox(ObjetoActor actor, float alto, float ancho, float offsetX,
			float offsetY) {
		super(alto, ancho);
		this.actor = actor;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public Rectangulo posicion() {
		return this.posicionar(actor.getX() + this.offsetX, actor.getY()
				+ this.offsetY);
	}

}
