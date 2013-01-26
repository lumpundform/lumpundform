package com.lumpundform.acciones;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.ObjetoActor;

/**
 * Las acciones que realizan todos los {@link ObjetoActor}es al llamar su
<<<<<<< HEAD
 * función {@link ObjetoActor#act(float)}.
 * 
 * @author Sergio Valencia
=======
 * función {@link ObjetoActor#act(float)}
 * 
 * @author sergio
>>>>>>> 388e50ad9cb0558a3b2e54f4c5e01fee93a0ea40
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
