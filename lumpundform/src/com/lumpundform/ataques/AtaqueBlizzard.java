package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.habilidades.Habilidad;

public class AtaqueBlizzard extends AtaqueSostenido {

	public AtaqueBlizzard(Personaje personaje, Habilidad habilidad) {
		super("ataque_blizzard", personaje, habilidad);

		setWidth(300.0f);
		setHeight(201.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));

		float offset = 120.0f;
		float offsetPos = getPersonaje().derecha() ? getPersonaje().getHitbox().getAncho() + offset : -offset
				- getHitbox().getAncho();
		setEsquinaX("inf-izq", getPersonaje().getEsquina("inf-izq").x + offsetPos);
		setEsquinaY("inf-izq", getPersonaje().getEsquina("inf-izq").y);

		setDireccionX(getPersonaje().getDireccionX());

		setDano(3.0f);

		cargarAnimaciones("normal");
	}

}
