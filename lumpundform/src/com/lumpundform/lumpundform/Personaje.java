package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje {
	private Texture texture;
	public TextureRegion normal;
	public int[] normalCoord = {1, 0};
	public int alto;
	public int ancho;
	public boolean enMovimiento;
	public float destinoX;
	public float posicionX;
	public float posicionY;
	private float velocidad;
	
	public Personaje() {
		posicionX = 20;
		posicionY = 20;
		destinoX = 0;
		enMovimiento = false;
		alto = 60;
		ancho = 35;
		velocidad = 0.00000001f;
		texture = new Texture(Gdx.files.internal("samus_sprite_sheet.png"));
		normal = new TextureRegion(texture, normalCoord[0] * ancho, normalCoord[1] * alto, ancho, alto);
	}
	
	public void caminar(float delta) {
		if (enMovimiento && destinoX != posicionX) {
			if (destinoX < posicionX) {
				posicionX -= velocidad * delta;
				if (posicionX < destinoX) {
					posicionX = destinoX;
					enMovimiento = false;
				}
			} else {
				posicionX += velocidad * delta;
				if (posicionX > destinoX) {
					posicionX = destinoX;
					enMovimiento = false;
				}
			}
		}
	}
}
