package com.lumpundform.utilerias;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fuentes {

	public static BitmapFont regular() {
		return new BitmapFont(Gdx.files.internal("data/font/font.fnt"),
				Gdx.files.internal("data/font/font.png"), false);
	}
}
