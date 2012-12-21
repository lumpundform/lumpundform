package com.lumpundform.acciones;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.Heroe;

public class HeroeAction extends Action {

	@Override
	public boolean act(float delta) {
		Heroe h = (Heroe) getActor();

		if (h != null) {
			h.setCooldownDano(h.getCooldownDano() - delta);

			h.actualizarTransparente(delta);

			h.moverHeroe(delta);
		}

		return false;
	}

}
