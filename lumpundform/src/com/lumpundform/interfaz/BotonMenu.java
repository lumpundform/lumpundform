package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.escenario.EscenarioBase;

class BotonMenu extends BotonSuperior {

	BotonMenu(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("texturas/manabar.png")), 1, 1, 1, 1), escenario);
		setWidth(anchoBoton());
		setHeight(UI.altoBoton);
		setxBase((getCamara().viewportWidth / 2) - (anchoBoton() / 2));
		setyBase(yBoton());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		CharSequence msg = "Menú";
		TextBounds tb = bmf.getBounds(msg);
		float xMenu = getX() + (anchoBoton() / 2) - (tb.width / 2);
		float yMenu = getY() + (UI.altoBoton / 2) + (tb.height / 2);

		bmf.draw(batch, msg, xMenu, yMenu);
	}
}
