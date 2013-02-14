package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.Ataque;

public class HabilidadSostenida extends Habilidad {
	protected Ataque ataque;

	protected HabilidadSostenida(Personaje actor, String nombre, float cooldownDefault, float manaMinimo) {
		super(actor, nombre, cooldownDefault);
		setSostenido(true);
		setManaMinimo(manaMinimo);
	}

	@Override
	public void ejecutar(Vector2 pos) {
		if (!sePuedeEjecutar()) {
			ataque = null;
			return;
		}

		getActor().setRegenerarMana(false);
		crearAtaque(ataque);
		setEjecutandose(true);
	}

	@Override
	public void detener() {
		if (ataque != null) {
			ataque.getAnimacion().terminar();
			getActor().setRegenerarMana(true);
			setEjecutandose(false);
		}
	}

}
