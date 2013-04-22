package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.AtaqueBlizzard;
import com.lumpundform.utilerias.U;

/**
 * La habilidad para el ataque blizzard
 * 
 * @author Sergio Valencia
 * 
 */
public class HabilidadBlizzard extends HabilidadSostenida implements HabilidadVariosAtaques {
	private float tiempoTranscurrido;
	private float tiempoEntreAtaques;

	protected HabilidadBlizzard(Personaje actor) {
		super(actor, "blizzard", 0.0f, 15.0f);
		setMana(3.0f);

		tiempoTranscurrido = 0.0f;
		tiempoEntreAtaques = 0.05f;
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
		crearAtaque(new AtaqueBlizzard(getActor(), this), false);
		tiempoTranscurrido = 0.0f;
	}

	@Override
	public void ejecutar(Vector2 pos) {
		U.l("tiempo", tiempoTranscurrido);
		ataque = new AtaqueBlizzard(getActor(), this);
		super.ejecutar(pos);
	}

}
