package com.lumpundform.ataques;

import com.badlogic.gdx.scenes.scene2d.Action;

public class AtaqueAction extends Action {

	@Override
	public boolean act(float delta) {
		Ataque a = (Ataque) getActor();

		if (a != null) {
			if (!a.destruirSiFueraDeCamara()) {
				return a.calcularColision();
			}
		}

		return true;
	}

}
