package com.lumpundform.acciones;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.ObjetoActor;

/**
 * Las acciones que realizan todos los {@link ObjetoActor}es al llamar su
 * función {@link ObjetoActor#act(float)}
 * 
 * @author Sergio Valencia
 * 
 */
public class ObjetoActorAction extends Action {

	@Override
	public boolean act(float delta) {
		ObjetoActor a = (ObjetoActor) getActor();

		if (a != null) {
			a.setTiempoTranscurrido(a.getTiempoTranscurrido() + delta);
		}

		return false;
	}

}
