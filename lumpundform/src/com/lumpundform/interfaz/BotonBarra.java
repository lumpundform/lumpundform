package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.escenario.EscenarioBase;

class BotonBarra extends BotonBase {
	private NinePatch barBackground;

	protected BotonBarra(NinePatch ninePatch, EscenarioBase escenario) {
		super(ninePatch, escenario);
		setyBase(getCamara().viewportHeight - UI.margen - UI.altoBoton
				- UI.margen - UI.altoBarra);
		barBackground = new NinePatch(new Texture(
				Gdx.files.internal("bar_background.png")), 1, 1, 1, 1);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		barBackground.draw(batch, getX(), getY(), UI.anchoBarra, UI.altoBarra);

		super.draw(batch, parentAlpha);
	}

}
