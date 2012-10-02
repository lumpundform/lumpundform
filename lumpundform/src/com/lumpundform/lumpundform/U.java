package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
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
}
