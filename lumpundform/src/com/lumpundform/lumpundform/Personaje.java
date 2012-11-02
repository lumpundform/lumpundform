package com.lumpundform.lumpundform;

import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase para todos los personajes del juego
 * 
 * @author Sergio
 * 
 */
public abstract class Personaje extends ObjetoActor {
	protected Map<String, Habilidad> habilidades;

	protected Personaje(String nombre, Vector2 puntoOrigen) {
		super(nombre);

		this.x = puntoOrigen.x;
		this.y = puntoOrigen.y;
	}

	@Override
	protected abstract void cargarAnimaciones();

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

		if (flip)
			cuadroActual.flip(true, false);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		estado = colisionPiso ? DETENIDO : CAYENDO;
		reducirCooldownHabilidades(delta);
	}

	@Override
	protected TextureRegion getCuadroActual() {
		// Revisar de cual animación se va a agarrar el cuadro actual
		String nombreAnimacion;
		switch (estado) {
		case DETENIDO:
		default:
			nombreAnimacion = "detenido";
			break;
		case MOVIMIENTO:
			nombreAnimacion = "corriendo";
			break;
		case CAYENDO:
			nombreAnimacion = "cayendo";
			break;
		}
		if (colisionActores) {
			nombreAnimacion = "colisionando";
		}

		if (!animacion.containsKey(nombreAnimacion)) {
			nombreAnimacion = "detenido";
		}

		return animacion.get(nombreAnimacion).getKeyFrame(tiempoTranscurrido,
				true);
	}

	private void reducirCooldownHabilidades(float delta) {
		Iterator<Habilidad> i = habilidades.values().iterator();
		while (i.hasNext()) {
			i.next().reducirCooldown(delta);
		}
	}
}
