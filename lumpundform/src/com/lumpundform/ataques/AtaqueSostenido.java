package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.habilidades.Habilidad;

public class AtaqueSostenido extends Ataque {

	protected AtaqueSostenido(String nombre, Personaje personaje, Habilidad habilidad) {
		super(nombre, personaje, habilidad);

		setQuitarConAnimacion(true);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		getPersonaje().quitarMana(getHabilidad().getMana(), getHabilidad().isSostenido());
		if (getPersonaje().getMana() <= 0) {
			getHabilidad().detener();
		}
	}
	
	@Override
	public void destruir() {
		super.destruir();
		getAnimacion().terminar();
	}

}
