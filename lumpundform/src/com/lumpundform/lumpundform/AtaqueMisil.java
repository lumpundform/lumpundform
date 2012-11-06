package com.lumpundform.lumpundform;

public class AtaqueMisil extends Ataque {

	protected AtaqueMisil(Personaje personaje) {
		super("misil", personaje);
		
		width = 100.0f;
		height = 100.0f;

		hitbox = new Rectangulo(height, width, true);
	}

	@Override
	protected void cargarAnimaciones() {
	}

}
