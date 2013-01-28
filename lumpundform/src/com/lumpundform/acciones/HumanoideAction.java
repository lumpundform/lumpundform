package com.lumpundform.acciones;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.Humanoide;

/**
 * Las acciones que realiza el {@link Humanoide} cada vez que se manda llamar
 * {@link Humanoide#act(float)}.
 * 
 * @author Sergio Valencia
 * 
 */
public class HumanoideAction extends Action {

	@Override
	public boolean act(float delta) {
		Humanoide h = (Humanoide) getActor();

		if (h != null) {
			if (h.isEnemigo() && h.hayHeroeEscenario()) {
				h.habilidad("disparar");
			}
		}

		return false;
	}

}
