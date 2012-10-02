package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Personaje extends ObjetoActor {

	protected Personaje(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);
	}
	
	
	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		boolean flip = false;
		TextureRegion cuadroActual = getCuadroActual();

		// Si está caminando al revés, voltea el sprite
		if (direccionX == IZQUIERDA) {
			cuadroActual.flip(true, false);
			flip = true;
		}

		batch.draw(cuadroActual, x, y);
		
		if (flip) cuadroActual.flip(true, false);
	}
	
	
	
	
	protected TextureRegion getCuadroActual() {
		// Revisar de cual animación se va a agarrar el cuadro actual
		String nombreAnimacion;
		switch (estado) {
		case DETENIDO:
		default:
			nombreAnimacion = "detenido";
			tiempoTranscurrido = 0f; // Reinicia el tiempo transcurrido para que
									 // empiece las animaciones desde el
									 // principio
			break;
		case MOVIMIENTO:
			nombreAnimacion = "corriendo";
			break;
		case CAYENDO:
			nombreAnimacion = "cayendo";
			break;
		}
		return animacion.get(nombreAnimacion)
				.getKeyFrame(tiempoTranscurrido, true);
	}
}
