package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class Utilerias {

	static public Vector3 coordenadasInput(Camera camara) {
		Vector3 posicion = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camara.unproject(posicion);
		
		return posicion;
	}
}
