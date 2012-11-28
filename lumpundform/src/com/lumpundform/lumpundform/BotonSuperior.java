package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.NinePatch;

public class BotonSuperior extends BotonBase {
	public BotonSuperior(NinePatch ninePatch, EscenarioBase escenario) {
		super(ninePatch, escenario);
	}

	protected float anchoBoton() {
		return ((camara.viewportWidth - (2 * UI.margen)) - (UI.margen * (UI.cantHabilidades)))
				/ (UI.cantHabilidades + 1);
	}

	protected float yBoton() {
		return camara.viewportHeight - UI.margen - height;
	}
}