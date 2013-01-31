package com.lumpundform.acciones;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.Heroe;

/**
 * Las acciones que realiza el {@link Heroe} cada vez que se manda llamar
 * {@link Heroe#act(float)}.
 * 
 * @author Sergio Valencia
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
