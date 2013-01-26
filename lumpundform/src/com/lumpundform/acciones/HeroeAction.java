package com.lumpundform.acciones;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.Heroe;

/**
 * Las acciones que realiza el {@link Heroe} cada vez que se manda llamar
<<<<<<< HEAD
 * {@link Heroe#act(float)}.
 * 
 * @author Sergio Valencia
=======
 * {@link Heroe#act(float)}
 * 
 * @author sergio
>>>>>>> 388e50ad9cb0558a3b2e54f4c5e01fee93a0ea40
 * 
 */
public class HeroeAction extends Action {

	@Override
	public boolean act(float delta) {
		Heroe h = (Heroe) getActor();

		if (h != null) {
			h.setCooldownDano(h.getCooldownDano() - delta);

			h.actualizarTransparente(delta);

			if (h.getMovimiento() == 1) {
				h.moverHeroe(delta, "derecha");
			} else if (h.getMovimiento() == -1) {
				h.moverHeroe(delta, "izquierda");
			}
		}

		return false;
	}

}
