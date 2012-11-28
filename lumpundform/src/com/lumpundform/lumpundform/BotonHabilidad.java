package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class BotonHabilidad extends BotonSuperior {
	public BotonHabilidad(EscenarioBase escenario, int offsetCounter) {
		this(escenario, offsetCounter, false);
	}

	public BotonHabilidad(EscenarioBase escenario, int offsetCounter,
			boolean derecha) {
		super(new NinePatch(new Texture(Gdx.files.internal("manabar.png")), 1,
				1, 1, 1), escenario);
		this.width = anchoBoton();
		this.height = UI.altoBoton;
		this.xBase = xHabilidad(offsetCounter, derecha);
		this.yBase = yBoton();
	}

	private float xHabilidad(int offset, boolean derecha) {
		if (derecha) {
			return UI.margen + ((camara.viewportWidth - (2 * UI.margen)))
					- anchoBoton()
					- (offset * (anchoBoton() + UI.margen));
		} else {
			return UI.margen + (offset * (anchoBoton() + UI.margen));
		}
	}

}
