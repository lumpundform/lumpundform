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
	private Animation animacionInicio;
	private Animation animacionLoop;
	private Animation animacionFin;

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
		float tiempoPorCuadro = 0.05f;

		SpriteSheet ss = D.ss(actor.getName(), tipo);

		Texture texturaAnimacion = Texturas.get(ss.getRuta());
		TextureRegion[][] tmp = TextureRegion.split(texturaAnimacion, (int) actor.getWidthTextura(),
				(int) actor.getHeightTextura());
		Array<TextureRegion> cuadrosAnimacion = new Array<TextureRegion>();
		for (int i = 0; i < ss.getRenglones(); i++) {
			for (int j = ss.getColumnasOffset(); j < ss.getColumnas() + ss.getColumnasOffset(); j++) {
				cuadrosAnimacion.add(tmp[i][j]);
			}
		}

		if (ss.getCuadrosInicio() > 0) {
			Array<TextureRegion> cuadrosInicio = new Array<TextureRegion>();
			for (int i = 0; i < ss.getCuadrosInicio(); i++) {
				cuadrosInicio.add(cuadrosAnimacion.get(i));
			}
			this.animacionInicio = new Animation(tiempoPorCuadro, cuadrosInicio);
			cuadrosAnimacion.removeAll(cuadrosInicio, true);
			cuadrosAnimacion.shrink();
		}
		if (ss.getCuadrosFin() > 0) {
			int cuadros = ss.getCuadrosFin();
			Array<TextureRegion> cuadrosFin = new Array<TextureRegion>();
			for (int cf = 0; cf < cuadros; cf++) {
				cuadrosFin.add(cuadrosAnimacion.get(cuadrosAnimacion.size - (cuadros - cf)));
			}
			this.animacionFin = new Animation(tiempoPorCuadro, cuadrosFin);
			cuadrosAnimacion.removeAll(cuadrosFin, true);
			cuadrosAnimacion.shrink();
		}
		this.animacionLoop = new Animation(tiempoPorCuadro, cuadrosAnimacion);
	}

	public Animation actual() {
		return this.animacionLoop;
	}

}
