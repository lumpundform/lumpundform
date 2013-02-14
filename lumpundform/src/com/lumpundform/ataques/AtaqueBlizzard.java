package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.habilidades.Habilidad;

public class AtaqueBlizzard extends AtaqueSostenido {

	public AtaqueBlizzard(Personaje personaje, Habilidad habilidad) {
		super("ataque_blizzard", personaje, habilidad);

		setWidth(28.0f);
		setHeight(28.0f);

		setHitbox(new Rectangulo(getWidth(), getHeight()));

		setVelocidad(35.0f);
		setVelocidadVertical(85.0f);

		cargarAnimaciones("normal");
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		moverDerecha(delta);
		moverAbajo(delta);
	}

}
