package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InterfazHelper {
	private SpriteBatch sb;
	private NinePatch lifebar;
	private NinePatch barBackground;

	public InterfazHelper() {
		sb = new SpriteBatch();
		lifebar = new NinePatch(new Texture(Gdx.files.internal("lifebar.png")),
				1, 1, 1, 1);
		barBackground = new NinePatch(new Texture(
				Gdx.files.internal("bar_background.png")), 1, 1, 1, 1);
	}

	public void dibujar(Heroe heroe) {
		sb.begin();
		lifebar(heroe);
		sb.end();
	}

	private void lifebar(Heroe heroe) {
		float ancho = 30;
		float alto = 250;
		
		float margen = 50;

		float y = Gdx.graphics.getHeight() - alto - margen;

		barBackground.draw(sb, margen, y, ancho, alto);
		lifebar.draw(sb, margen, y, ancho, alto * heroe.vida / heroe.vidaMaxima);
	}

}
