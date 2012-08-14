package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Heroe extends Personaje {
	/**
	 * Inicializa al héroe con valores específicos del héroe
	 */
	public Heroe() {
		super();
		
		ancho = 125;
		alto = 150;
		posicionX = 20 + (ancho / 2);
		posicionY = 20;

		estado = DE_PIE;
		destinoX = posicionX;
		direccionX = DERECHA;
		velocidad = 500;

		textura = new Texture(Gdx.files.internal("samus_sprite_sheet_crop.png"));
		coordNormal = new Coordenada(0, 0);
		spriteNormal = new Sprite(textura, coordNormal.x * ancho, coordNormal.y * alto, ancho, alto);
		spriteNormal.setPosition(posicionX, posicionY);
		
		initAnimacion();
	}
}
