package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.habilidades.Habilidad;

/**
 * El ataque para la habilidad
 * {@link com.lumpundform.habilidades.HabilidadEscudo}. Aunque no sea un ataque
 * en sí, se inicializa ya que tiene animación, posición y tamaño.
 * 
 * @author Sergio Valencia
 * 
 */
public class AtaqueEscudo extends Ataque {
	private Habilidad habilidad;

	public AtaqueEscudo(Personaje personaje, Habilidad habilidad) {
		super("ataque_escudo", personaje);

		setWidth(240.0f);
		setWidthTextura(120.0f);
		setHeight(150.0f);
		setHeightTextura(71.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth() * 0.65f));

		setHabilidad(habilidad);

		getPersonaje().setRegenerarMana(false);

		actualizarPosicion();

		setQuitarConAnimacion(true);

		cargarAnimaciones("normal");
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		actualizarPosicion();
		getPersonaje().quitarMana(getHabilidad().getMana(), getHabilidad().isSostenido());
		if (getPersonaje().getMana() <= 0) {
			getHabilidad().detener();
		}
	}

	@Override
	public void quitar() {
		getPersonaje().setRegenerarMana(true);
		super.quitar();
	}

	/**
	 * Actualiza la posición del ataque. Permite que el personaje se pueda mover
	 * sin "dejar atras" al escudo.
	 */
	private void actualizarPosicion() {
		float mitadPersonaje = getPersonaje().getHitbox().getAncho() / 2;
		float mitadAtaque = getHitbox().getAncho() / 2;
		setY(getPersonaje().getY());
		setX(getPersonaje().getX() + mitadPersonaje - mitadAtaque);
	}

	public Habilidad getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}

}
