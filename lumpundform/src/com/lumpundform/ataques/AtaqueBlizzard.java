package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.habilidades.Habilidad;

public class AtaqueBlizzard extends AtaqueSostenido {

	public AtaqueBlizzard(Personaje personaje, Habilidad habilidad,
			float xAtaque) {
		super("ataque_blizzard", personaje, habilidad);

		setWidth(40.0f);
		setWidthTextura(121.0f);
		setHeight(36.0f);
		setHeightTextura(109.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));

		float offset = 120.0f;
		float offsetPos = getPersonaje().derecha() ? getPersonaje().getHitbox()
				.getAncho() + offset : -offset - getHitbox().getAncho();
		float xAtaqueDireccion = getPersonaje().derecha() ? xAtaque : -xAtaque;
		setEsquinaX("inf-izq", getPersonaje().getEsquina("inf-izq").x
				+ offsetPos + xAtaqueDireccion);
		setEsquinaY("sup-izq", getPersonaje().getEsquina("sup-izq").y);

		setDireccionX(getPersonaje().getDireccionX());

		setVelocidad(50.0f);
		setVelocidadVertical(350.0f);

		setDano(30.0f);

		cargarAnimaciones("normal");
	}

	@Override
	public void act(float delta) {
		if (isColisionPiso()) {
			getAnimacion().terminar();
		} else if (!isDestruir()) {
			moverAbajo(delta);
			moverEnDireccion();
		}
		super.act(delta);
	}

}
