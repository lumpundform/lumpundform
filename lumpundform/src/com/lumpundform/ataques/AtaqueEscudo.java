package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.habilidades.Habilidad;

public class AtaqueEscudo extends Ataque {
	private Habilidad habilidad;

	public AtaqueEscudo(Personaje personaje, Habilidad habilidad) {
		super("ataque_escudo", personaje);

		setWidth(120.0f);
		setHeight(71.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));

		setHabilidad(habilidad);

		getPersonaje().setRegenerarMana(false);

		actualizarPosicion();

		cargarAnimaciones("normal");
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		actualizarPosicion();
		getPersonaje().quitarMana(getHabilidad().getMana(), getHabilidad().isSostenido());
	}

	@Override
	public void quitar() {
		getPersonaje().setRegenerarMana(true);
		super.quitar();
	}

	private void actualizarPosicion() {
		setY(getPersonaje().getY());
		setX(getPersonaje().getX());
	}

	public Habilidad getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}

}
