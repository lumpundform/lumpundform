package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.habilidades.Habilidad;

public class AtaqueBlizzard extends AtaqueSostenido {

	public AtaqueBlizzard(Personaje personaje, Habilidad habilidad) {
		super("ataque_blizzard", personaje, habilidad);

		setWidth(40.0f);
		setWidthTextura(121.0f);
		setHeight(36.0f);
		setHeightTextura(109.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));

		float offset = 120.0f;
		float offsetPos = getPersonaje().derecha() ? getPersonaje().getHitbox()
				.getAncho() + offset : -offset - getHitbox().getAncho();
		setEsquinaX("inf-izq", getPersonaje().getEsquina("inf-izq").x
				+ offsetPos);
		setEsquinaY("sup-izq", getPersonaje().getEsquina("sup-izq").y);

		setDireccionX(getPersonaje().getDireccionX());

		setVelocidad(50.0f);
		setVelocidadVertical(350.0f);

		setDano(3.0f);

		cargarAnimaciones("normal");
	}

	@Override
	public void act(float delta) {
		if (isColisionPiso()) {
			getAnimacion().terminar();
		} else {
			moverAbajo(delta);
			moverEnDireccion();
		}
		super.act(delta);
	}

}
