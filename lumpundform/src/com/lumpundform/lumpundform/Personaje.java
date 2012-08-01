package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Personaje {
	// Posición y Tamaño
	public int ancho;
	public int alto;
	public float posicionX;
	public float posicionY;

	// Movimiento
	public boolean enMovimiento;
	public float destinoX;
	public boolean direccionX; // false = izquierda, true = derecha
	private float velocidad;
	
	// Textura
	private Texture textura;
	public Sprite spriteNormal;
	public Coordenada coordNormal;
	
	/**
	 * Inicializa la posición, el tamaño, el movimiento y la textura del personaje
	 * 
	 * TODO: hacer sobrecarga al constructor para poder pasarle diferente cantidad de atributos
	 * y quitar los valores específicos del héroe del constructor.
	 */
	public Personaje() {
		ancho = 88;
		alto = 150;
		posicionX = 20 + (ancho / 2);
		posicionY = 20;

		enMovimiento = false;
		destinoX = 0;
		direccionX = true;
		velocidad = 500;

		textura = new Texture(Gdx.files.internal("samus_sprite_sheet_crop.gif"));
		coordNormal = new Coordenada(1, 0);
		spriteNormal = new Sprite(textura, coordNormal.x * ancho, coordNormal.y * alto, ancho, alto);
		spriteNormal.setPosition(posicionX, posicionY);
	}
	
	/**
	 * Si el personaje está en movimiento, actualiza su posición de acuerdo a su velocidad
	 * y al delta.
	 * @param delta Tiempo transcurrido desde el último frame en milisegundos. Se puede obtener con
	 * Gdx.graphics.getDeltaTime() dentro de render();
	 */
	public void caminar(float delta) {
		if (enMovimiento && destinoX != posicionX) {
			if (destinoX < posicionX) { // Se está moviendo hacia la izquierda
				direccionX = false;
				posicionX -= velocidad * delta;
				if (posicionX < destinoX) {
					posicionX = destinoX;
					enMovimiento = false;
				}
			} else { // Se está moviendo hacia la derecha
				direccionX = true;
				posicionX += velocidad * delta;
				if (posicionX > destinoX) {
					posicionX = destinoX;
					enMovimiento = false;
				}
			}
		}
		spriteNormal.setPosition(posicionX - (ancho / 2), posicionY);
	}

	/**
	 * Dibuja el sprite del héroe en pantalla dependiendo la dirección que tenga
	 * @param batch El batch en donde se va a dibujar
	 */
	public void dibujar(SpriteBatch batch) {
		boolean flip = false;
		// Si está caminando al revés, voltea el sprite
		if (!direccionX) {
			spriteNormal.flip(true, false);
			flip = true;
		}
		spriteNormal.draw(batch);
		
		// Después de dibujarlo, lo vuelve a voltear si se volteó para dejarlo en posición normal
		if (flip) spriteNormal.flip(true, false);
	}
}
