package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.utilerias.Fuentes;

class BotonMenu extends BotonSuperior {
	BitmapFont bmf = Fuentes.regular();
	
	BotonMenu(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("manabar.png")), 1,
				1, 1, 1), escenario);
		width = anchoBoton();
		height = UI.altoBoton;
		setxBase((getCamara().viewportWidth / 2) - (anchoBoton() / 2));
		setyBase(yBoton());
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		CharSequence msg = "Menú";
		TextBounds tb = bmf.getBounds(msg);
		float xMenu = x + (anchoBoton() / 2) - (tb.width / 2);
		float yMenu = y + (UI.altoBoton / 2) + (tb.height / 2);

		bmf.draw(batch, msg, xMenu, yMenu);
	}
}
