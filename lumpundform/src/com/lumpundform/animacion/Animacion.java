package com.lumpundform.animacion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.utilerias.D;
import com.lumpundform.utilerias.SpriteSheet;
import com.lumpundform.utilerias.Texturas;

/**
 * Guarda las animaciones a usarse en el juego. Puede tener mas de una
 * {@link Animation} si es que la animación tiene principio y fin.
 * 
 * @author Sergio Valencia
 * 
 */
public class Animacion {
	private float tiempoPorCuadro;

	// Animaciones
	private Animation animacionInicio;
	private Animation animacionLoop;
	private Animation animacionFin;

	// Cuadros
	private Array<TextureRegion> cuadrosInicio;
	private Array<TextureRegion> cuadrosLoop;
	private Array<TextureRegion> cuadrosFin;

	/**
	 * Se busca en el archivo datos.xml de acuerdo al nombre del
	 * {@link ObjetoActor} y al tipo de animación para generar la
	 * {@link Animacion} correcta.
	 * 
	 * @param actor
	 *            El {@link ObjetoActor} del cual leer la animación
	 * @param tipo
	 *            El nombre de la animación.
	 */
	public Animacion(ObjetoActor actor, String tipo) {
		tiempoPorCuadro = 0.05f;

		SpriteSheet ss = D.ss(actor.getName(), tipo);

		Texture texturaAnimacion = Texturas.get(ss.getRuta());
		TextureRegion[][] tmp = TextureRegion.split(texturaAnimacion, (int) actor.getWidthTextura(),
				(int) actor.getHeightTextura());
		cuadrosLoop = new Array<TextureRegion>();
		for (int i = 0; i < ss.getRenglones(); i++) {
			for (int j = ss.getColumnasOffset(); j < ss.getColumnas() + ss.getColumnasOffset(); j++) {
				cuadrosLoop.add(tmp[i][j]);
			}
		}

		if (ss.getCuadrosInicio() > 0) {
			cuadrosInicio = new Array<TextureRegion>();
			for (int i = 0; i < ss.getCuadrosInicio(); i++) {
				cuadrosInicio.add(cuadrosLoop.get(i));
			}
			this.animacionInicio = new Animation(tiempoPorCuadro, cuadrosInicio);
			cuadrosLoop.removeAll(cuadrosInicio, true);
			cuadrosLoop.shrink();
		}
		if (ss.getCuadrosFin() > 0) {
			int cuadros = ss.getCuadrosFin();
			cuadrosFin = new Array<TextureRegion>();
			for (int cf = 0; cf < cuadros; cf++) {
				cuadrosFin.add(cuadrosLoop.get(cuadrosLoop.size - (cuadros - cf)));
			}
			this.animacionFin = new Animation(tiempoPorCuadro, cuadrosFin);
			cuadrosLoop.removeAll(cuadrosFin, true);
			cuadrosLoop.shrink();
		}
		this.animacionLoop = new Animation(tiempoPorCuadro, cuadrosLoop);
	}

	/**
	 * Calcula cuál es la animación a usar (inicio, loop o fin) dependiendo del
	 * tiempo transcurrido.
	 * 
	 * @param tiempo
	 *            El tiempoTranscurrido.
	 * @return La {@link Animation} a usar.
	 */
	public Animation actual(float tiempo) {
		if (animacionInicio != null && tiempo <= tiempoPorCuadro * cuadrosInicio.size) {
			return animacionInicio;
		} else {
			return this.animacionLoop;
		}
	}

}
