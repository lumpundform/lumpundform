package com.lumpundform.interfaz;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.lumpundform.escenario.EscenarioBase;

class BotonSuperior extends BotonBase {
	protected BotonSuperior(NinePatch ninePatch, EscenarioBase escenario) {
		super(ninePatch, escenario);
	}

	protected float anchoBoton() {
		return ((getCamara().viewportWidth - (2 * UI.margen)) - (UI.margen * (UI.cantHabilidades)))
				/ (UI.cantHabilidades + 1);
	}

	protected float yBoton() {
		return getCamara().viewportHeight - UI.margen - getHeight();
	}
}
