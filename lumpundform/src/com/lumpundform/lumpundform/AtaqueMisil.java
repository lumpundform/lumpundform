package com.lumpundform.lumpundform;

public class AtaqueMisil extends Ataque {

	protected AtaqueMisil(Personaje personaje) {
		super("ataque_misil", personaje);

		width = 100.0f;
		height = 100.0f;

		hitbox = new Rectangulo(height * 0.2f, width * 0.55f, true);

		estado = Estado.NORMAL;
		velocidad = 800.0f;
		direccionX = personaje.direccionX;

		dano = 30.0f;

		y = personaje.y + (personaje.getHitbox().getAlto() / 2) - 33;

		float xNueva;
		if (derecha()) {
			xNueva = personaje.getSensor("inf-izq").x + 30;
		} else {
			xNueva = personaje.getSensor("inf-izq").x - 40;
		}
		setSensorX("inf-izq", xNueva);

		cargarAnimaciones("normal", "explosion");
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (estado == Estado.NORMAL) {
			if (derecha()) {
				moverDerecha(delta);
			} else {
				moverIzquierda(delta);
			}
		}
	}

	@Override
	public void destruir() {
		if (!destruir) {
			super.destruir();
			tiempoTranscurrido = 0.0f;
			estado = Estado.EXPLOTANDO;
		}
	}

}
