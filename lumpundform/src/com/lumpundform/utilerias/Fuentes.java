package com.lumpundform.utilerias;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fuentes {
	public static Color colorDefault = new Color(1.0f, 1.0f, 1.0f, 1.0f);

	public static BitmapFont regular() {
		BitmapFont fuente = new BitmapFont(Gdx.files.internal("data/font/font.fnt"),
				Gdx.files.internal("data/font/font.png"), false);
		fuente.setColor(colorDefault);
		return fuente;
	}
}
