package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.habilidades.Habilidad;

public class AtaqueSostenido extends Ataque {

	// Habilidad
	private Habilidad habilidad;

	protected AtaqueSostenido(String nombre, Personaje personaje, Habilidad habilidad) {
		super(nombre, personaje);

		setHabilidad(habilidad);

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

	public Habilidad getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}

}
