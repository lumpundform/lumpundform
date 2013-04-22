package com.lumpundform.habilidades;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.AtaqueBlizzard;

/**
 * La habilidad para el ataque blizzard
 * 
 * @author Sergio Valencia
 * 
 */
public class HabilidadBlizzard extends HabilidadSostenida implements
		HabilidadVariosAtaques {
	private float tiempoTranscurrido;
	private float tiempoEntreAtaques;
	private float anchoHabilidad;
	private Random random;

	protected HabilidadBlizzard(Personaje actor) {
		super(actor, "blizzard", 0.0f, 15.0f);
		setMana(3.0f);

		tiempoTranscurrido = 0.0f;
		tiempoEntreAtaques = 0.1f;
		anchoHabilidad = 200.0f;

		random = new Random();
	}

	@Override
	public void aumentarTiempoTranscurrido(float delta) {
		tiempoTranscurrido += delta;
		revisarTiempoTranscurrido();
	}

	@Override
	public void revisarTiempoTranscurrido() {
		if (tiempoTranscurrido >= tiempoEntreAtaques) {
			crearNuevoAtaque();
		}
	}

	@Override
	public void crearNuevoAtaque() {
		crearAtaque(new AtaqueBlizzard(getActor(), this, random.nextFloat()
				* anchoHabilidad), false);
		tiempoTranscurrido = 0.0f;
	}

	@Override
	public void ejecutar(Vector2 pos) {
		ataque = new AtaqueBlizzard(getActor(), this, random.nextFloat()
				* anchoHabilidad);
		super.ejecutar(pos);
	}

}
