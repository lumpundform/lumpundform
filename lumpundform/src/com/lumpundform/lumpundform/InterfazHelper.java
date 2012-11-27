package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class InterfazHelper {
	private SpriteBatch sb;
	private CamaraJuego camara;
	private NinePatch lifebar;
	private NinePatch manabar;
	private NinePatch botonMenu;
	private NinePatch botonHabilidad;
	private NinePatch barBackground;
	private float anchoBarras;
	private float altoBarras;
	private float altoBotones;
	private float margen;
	private int cantBotones;

	public InterfazHelper(CamaraJuego cam) {
		sb = new SpriteBatch();
		camara = cam;
		lifebar = new NinePatch(new Texture(Gdx.files.internal("lifebar.png")),
				1, 1, 1, 1);
		manabar = new NinePatch(new Texture(Gdx.files.internal("manabar.png")),
				1, 1, 1, 1);
		botonMenu = new NinePatch(
				new Texture(Gdx.files.internal("manabar.png")), 1, 1, 1, 1);
		botonHabilidad = new NinePatch(new Texture(
				Gdx.files.internal("manabar.png")), 1, 1, 1, 1);
		barBackground = new NinePatch(new Texture(
				Gdx.files.internal("bar_background.png")), 1, 1, 1, 1);
		anchoBarras = 30;
		altoBarras = 250;
		altoBotones = 80;
		margen = 30;
		cantBotones = 6;
	}

	public void dibujar(Heroe heroe) {
		sb.begin();
		botonMenu();
		habilidades();
		lifebar(heroe);
		manabar(heroe);
		sb.end();
	}

	private void botonMenu() {
		float x = (camara.viewportWidth / 2) - (anchoBotones() / 2);
		float y = yBotones();
		float anchoBotones = anchoBotones();
		botonMenu.draw(sb, x, y, anchoBotones, altoBotones);

		BitmapFont bmf = Fuentes.regular();
		CharSequence msg = "Menú";
		TextBounds tb = bmf.getBounds(msg);
		float xMenu = x + (anchoBotones / 2) - (tb.width / 2);
		float yMenu = y + (altoBotones / 2) + (tb.height / 2);

		bmf.draw(sb, msg, xMenu, yMenu);
	}

	private void habilidades() {
		float y = yBotones();
		float x = margen;
		float ancho = (camara.viewportWidth - (2 * margen));
		float anchoBotones = anchoBotones();

		for (int i = 0; i < (cantBotones / 2); i++) {
			botonHabilidad.draw(sb, (x + (i * (anchoBotones + margen))), y,
					anchoBotones, altoBotones);
			botonHabilidad.draw(sb, x + ancho - anchoBotones
					- (i * (anchoBotones + margen)), y, anchoBotones,
					altoBotones);
		}
	}

	private void lifebar(Heroe heroe) {
		float y = camara.viewportHeight - margen - altoBotones - margen
				- altoBarras;
		float x = margen;

		barBackground.draw(sb, x, y, anchoBarras, altoBarras);
		lifebar.draw(sb, x, y, anchoBarras, altoBarras * heroe.vida
				/ heroe.vidaMax);
	}

	private void manabar(Heroe heroe) {
		float y = camara.viewportHeight - margen - altoBotones - margen
				- altoBarras;
		float x = camara.viewportWidth - anchoBarras - margen;

		barBackground.draw(sb, x, y, anchoBarras, altoBarras);
		manabar.draw(sb, x, y, anchoBarras, altoBarras * heroe.mana
				/ heroe.manaMax);
	}

	private float anchoBotones() {
		return ((camara.viewportWidth - (2 * margen)) - (margen * (cantBotones)))
				/ (cantBotones + 1);
	}

	private float yBotones() {
		return camara.viewportHeight - margen - altoBotones;
	}

}
