package com.lumpundform.animacion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private Animation animacionLoop;

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
		SpriteSheet ss = D.ss(actor.getName(), tipo);

		Texture texturaAnimacion = Texturas.get(ss.getRuta());
		TextureRegion[][] tmp = TextureRegion.split(texturaAnimacion, (int) actor.getWidthTextura(),
				(int) actor.getHeightTextura());
		TextureRegion[] cuadrosAnimacion = new TextureRegion[ss.getColumnas() * ss.getRenglones()];
		int index = 0;
		for (int i = 0; i < ss.getRenglones(); i++) {
			for (int j = ss.getColumnasOffset(); j < ss.getColumnas() + ss.getColumnasOffset(); j++) {
				cuadrosAnimacion[index++] = tmp[i][j];
			}
		}
		
		this.animacionLoop = new Animation(0.05f, cuadrosAnimacion);
	}
	
	public Animation actual() {
		return this.animacionLoop;
	}

}
