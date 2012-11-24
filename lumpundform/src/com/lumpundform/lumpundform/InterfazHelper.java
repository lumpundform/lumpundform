package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InterfazHelper {
	private SpriteBatch sb;
	private NinePatch lifebar;
	private NinePatch manabar;
	private NinePatch barBackground;
	private float anchoBarras;
	private float altoBarras;
	private float margenBarras;

	public InterfazHelper() {
		sb = new SpriteBatch();
		lifebar = new NinePatch(new Texture(Gdx.files.internal("lifebar.png")),
				1, 1, 1, 1);
		manabar = new NinePatch(new Texture(Gdx.files.internal("manabar.png")),
				1, 1, 1, 1);
		barBackground = new NinePatch(new Texture(
				Gdx.files.internal("bar_background.png")), 1, 1, 1, 1);
		anchoBarras = 30;
		altoBarras = 250;
		margenBarras = 50;
	}

	public void dibujar(Heroe heroe) {
		sb.begin();
		lifebar(heroe);
		manabar(heroe);
		sb.end();
	}

	private void lifebar(Heroe heroe) {
		float y = Gdx.graphics.getHeight() - altoBarras - margenBarras;
		float x = margenBarras;

		barBackground.draw(sb, x, y, anchoBarras, altoBarras);
		lifebar.draw(sb, x, y, anchoBarras, altoBarras * heroe.vida / heroe.vidaMax);
	}
	
	private void manabar(Heroe heroe) {
		float y = Gdx.graphics.getHeight() - altoBarras - margenBarras;
		float x = Gdx.graphics.getWidth() - anchoBarras - margenBarras;

		barBackground.draw(sb, x, y, anchoBarras, altoBarras);
		manabar.draw(sb, x, y, anchoBarras, altoBarras * heroe.mana / heroe.manaMax);
	}

}
