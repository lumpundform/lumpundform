package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BotonBarra extends BotonBase {
	private NinePatch barBackground;

	public BotonBarra(NinePatch ninePatch, EscenarioBase escenario) {
		super(ninePatch, escenario);
		yBase = camara.viewportHeight - UI.margen - UI.altoBoton - UI.margen
				- UI.altoBarra;
		barBackground = new NinePatch(new Texture(
				Gdx.files.internal("bar_background.png")), 1, 1, 1, 1);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		barBackground.draw(batch, x, y, UI.anchoBarra, UI.altoBarra);
		
		super.draw(batch, parentAlpha);
	}

}
