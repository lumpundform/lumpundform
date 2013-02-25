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

		cargarAnimaciones("normal");
	}

}
