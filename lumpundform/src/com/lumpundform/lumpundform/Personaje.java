package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	public float velocidad;
	
	// Textura
	private Texture textura;
	public Sprite spriteNormal;
	public Coordenada coordNormal;
	private Texture texturaCorriendo;
	private static final int		COLUMNAS_CORRIENDO = 11; 
	private static final int		RENGLONES_CORRIENDO = 1;
	private TextureRegion[] cuadrosCorriendo;
	public TextureRegion cuadroActual;
	private Animation animacionCorriendo;
	public float tiempoEstado;
	
	/**
	 * Inicializa la posición, el tamaño, el movimiento y la textura del personaje
	 * 
	 * TODO: hacer sobrecarga al constructor para poder pasarle diferente cantidad de atributos
	 * y quitar los valores específicos del héroe del constructor.
	 */
	public Personaje() {
		ancho = 125;
		alto = 150;
		posicionX = 20 + (ancho / 2);
		posicionY = 20;

		enMovimiento = false;
		destinoX = 0;
		direccionX = true;
		velocidad = 500;

		textura = new Texture(Gdx.files.internal("samus_sprite_sheet_crop.png"));
		coordNormal = new Coordenada(0, 0);
		spriteNormal = new Sprite(textura, coordNormal.x * ancho, coordNormal.y * alto, ancho, alto);
		spriteNormal.setPosition(posicionX, posicionY);
		
		texturaCorriendo = new Texture(Gdx.files.internal("samus_corriendo.png"));
		TextureRegion[][] tmp = TextureRegion.split(texturaCorriendo, ancho, alto);
		cuadrosCorriendo = new TextureRegion[COLUMNAS_CORRIENDO * RENGLONES_CORRIENDO];
		int index = 0;
		for (int i = 0; i < RENGLONES_CORRIENDO; i++) {
            for (int j = 0; j < COLUMNAS_CORRIENDO; j++) {
                    cuadrosCorriendo[index++] = tmp[i][j];
            }
		}
		animacionCorriendo = new Animation(0.05f, cuadrosCorriendo);
		tiempoEstado = 0f;
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
	public void dibujar(SpriteBatch batch, float delta) {
		tiempoEstado += delta;
		cuadroActual = animacionCorriendo.getKeyFrame(tiempoEstado, true);
		
		boolean flip = false;
		// Si está caminando al revés, voltea el sprite
		if (direccionX) {
			spriteNormal.flip(true, false);
			cuadroActual.flip(true, false);
			flip = true;
		}
		
		if (enMovimiento) {
			batch.draw(cuadroActual, posicionX, posicionY);
		} else {
			spriteNormal.draw(batch);
		}
		
		// Después de dibujarlo, lo vuelve a voltear si se volteó para dejarlo en posición normal
		if (flip) {
			spriteNormal.flip(true, false);
			cuadroActual.flip(true, false);
		}
	}
}
