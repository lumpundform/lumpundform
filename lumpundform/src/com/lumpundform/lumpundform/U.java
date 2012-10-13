package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Clase de funciones de Utileria
 * @author Sergio
 *
 */
public class U {

	static public Vector3 voltearCoordenadas(Camera camara, int x, int y) {
		Vector3 posicion = new Vector3(x, y, 0);
		camara.unproject(posicion);
		
		return posicion;
	}
	
	static public Vector3 voltearCoordenadasInput(Camera camara) {
		return voltearCoordenadas(camara, Gdx.input.getX(), Gdx.input.getY());
	}
	
	static public void dibujarLineasColision(Poligono poligono) {
		Vector2[] puntos = poligono.puntos;
		ShapeRenderer sr = new ShapeRenderer();
		
		sr.begin(ShapeType.Line);
		sr.setColor(1, 1, 1, 1);
		for (int i = 0; i < puntos.length; i++) {
			int i2 = (i + 1 >= puntos.length) ? 0 : i + 1;
			sr.line(puntos[i].x, puntos[i].y, puntos[i2].x, puntos[i2].y);
		}
		sr.end();
	}
	
	/**
	 * Función atajo de Gdx.app.log
	 * @param tag
	 * @param mensaje
	 */
	static public void l(String tag, Object mensaje) {
		Gdx.app.log(tag, mensaje + "");
	}
}
