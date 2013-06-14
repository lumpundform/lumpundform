package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Hitbox;
import com.lumpundform.habilidades.Habilidad;

/**
 * El ataque para la habilidad
 * {@link com.lumpundform.habilidades.HabilidadEscudo}. Aunque no sea un ataque
 * en sí, se inicializa ya que tiene animación, posición y tamaño.
 * 
 * @author Sergio Valencia
 * 
 */
public class AtaqueEscudo extends AtaqueSostenido {

	public AtaqueEscudo(Personaje personaje, Habilidad habilidad) {
		super("ataque_escudo", personaje, habilidad);

		setWidth(240.0f);
		setWidthTextura(120.0f);
		setHeight(150.0f);
		setHeightTextura(71.0f);

		setHitboxDefault(new Hitbox(this, getHeight(), getWidth() * 0.65f));

		actualizarPosicion();

		cargarAnimaciones("normal");
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		actualizarPosicion();
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

}
