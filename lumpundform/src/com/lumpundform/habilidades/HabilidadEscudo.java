package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Personaje;
import com.lumpundform.ataques.AtaqueEscudo;

/**
 * La habilidad escudo del {@link com.lumpundform.actores.Heroe}
 * 
 * @author Sergio Valencia
 * 
 */
public class HabilidadEscudo extends HabilidadSostenida {

	protected HabilidadEscudo(Personaje actor) {
		super(actor, "escudo", 0.0f, 5.0f);
		setMana(10.0f);
	}

	@Override
	public void ejecutar(Vector2 pos) {
		ataque = new AtaqueEscudo(getActor(), this);
		super.ejecutar(pos);
	}

}
