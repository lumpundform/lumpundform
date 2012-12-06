package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;

public class AtaqueMisil extends Ataque {

	public AtaqueMisil(Personaje personaje) {
		super("ataque_misil", personaje);

		width = 100.0f;
		height = 100.0f;

		setHitbox(new Rectangulo(height * 0.2f, width * 0.55f));

		setEstado(Estado.NORMAL);
		setVelocidad(800.0f);
		setDireccionX(personaje.getDireccionX());

		setDano(30.0f);

		y = personaje.y + (personaje.getHitbox().getAlto() / 2) - 33;

		float xNueva;
		if (derecha()) {
			xNueva = personaje.getEsquina("inf-izq").x + 30;
		} else {
			xNueva = personaje.getEsquina("inf-izq").x - 40;
		}
		setEsquinaX("inf-izq", xNueva);

		cargarAnimaciones("normal", "explosion");
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (getEstado() == Estado.NORMAL) {
			if (derecha()) {
				moverDerecha(delta);
			} else {
				moverIzquierda(delta);
			}
		}
	}

	@Override
	public void destruir() {
		if (!isDestruir()) {
			super.destruir();
			setTiempoTranscurrido(0.0f);
			setEstado(Estado.EXPLOTANDO);
		}
	}

}
