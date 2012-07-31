package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje {
	private Texture texture;
	public int direccion;
	public TextureRegion normal;
	public TextureRegion anormal;
	public int[] normalCoord = {1, 0};
	public int[] anormalCoord = {3, 1};
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
		alto = 150;
		ancho = 88;
		velocidad = 500;
		texture = new Texture(Gdx.files.internal("samus_sprite_sheet.png"));
		normal = new TextureRegion(texture, normalCoord[0] * ancho, normalCoord[1] * alto, ancho, alto);
		anormal = new TextureRegion(texture, anormalCoord[0] * ancho, anormalCoord[1] * alto, ancho, alto);
	}
	
	public void caminar(float delta) {
		Gdx.app.debug("Velocidad", "Velocidad: " + velocidad);
		if (enMovimiento && destinoX != posicionX) {
			if (destinoX < posicionX) {
				direccion = 1;
				posicionX -= velocidad * delta;
				if (posicionX < destinoX) {
					posicionX = destinoX;
					enMovimiento = false;
				}
			} else {
				posicionX += velocidad * delta;
				direccion = 2;
				if (posicionX > destinoX) {
					posicionX = destinoX;
					enMovimiento = false;
				}
			}
		}
	}
}
