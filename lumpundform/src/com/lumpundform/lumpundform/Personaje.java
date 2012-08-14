package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje {
	// Valores Estáticos
	public static final int 		DE_PIE = 0;
	public static final int 		MOVIMIENTO = 1;
	public static final int 		BRINCANDO = 2;
	public static final int 		IZQUIERDA = 10;
	public static final int 		DERECHA = 11;
	public static final int 		ARRIBA = 20;
	public static final int 		ABAJO = 21;
	
	protected static final int		COLUMNAS_CORRIENDO = 11; 
	protected static final int		RENGLONES_CORRIENDO = 1;
	
	// Estado, Posición y Tamaño
	public int estado;
	public int direccionX;
	public int direccionY;
	public int ancho;
	public int alto;
	public float posicionX;
	public float posicionY;

	// Movimiento
	public float destinoX;
	public float velocidad;
	
	// Textura
	protected Texture textura;
	public Sprite spriteNormal;
	public Coordenada coordNormal;
	protected Texture texturaCorriendo;
	protected TextureRegion[] cuadrosCorriendo;
	public TextureRegion cuadroActual;
	protected Animation animacionCorriendo;
	public float tiempoEstado;
	
	/**
	 * Inicializa la posición, el tamaño, el movimiento y la textura del personaje
	 * 
	 * TODO: hacer sobrecarga al constructor para poder pasarle diferente cantidad de atributos
	 * y quitar los valores específicos del héroe del constructor.
	 */
	public Personaje() {
	}
	
	/**
	 * Si el personaje está en movimiento, actualiza su posición de acuerdo a su velocidad
	 * y al delta.
	 * @param delta Tiempo transcurrido desde el último frame en milisegundos. Se puede obtener con
	 * Gdx.graphics.getDeltaTime() dentro de render();
	 */
	public void caminar(float delta) {
		if (destinoX != posicionX) {
			estado = MOVIMIENTO;
			if (destinoX < posicionX) { // Se está moviendo hacia la izquierda
				direccionX = IZQUIERDA;
				posicionX -= velocidad * delta;
				if (posicionX < destinoX) {
					posicionX = destinoX;
					estado = DE_PIE;
				}
			} else { // Se está moviendo hacia la derecha
				direccionX = DERECHA;
				posicionX += velocidad * delta;
				if (posicionX > destinoX) {
					posicionX = destinoX;
					estado = DE_PIE;
				}
			}
		} else {
			estado = DE_PIE;
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
		if (direccionX == DERECHA) {
			spriteNormal.flip(true, false);
			cuadroActual.flip(true, false);
			flip = true;
		}
		
		if (estado == MOVIMIENTO) {
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
	
	protected void initAnimacion() {
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
}
