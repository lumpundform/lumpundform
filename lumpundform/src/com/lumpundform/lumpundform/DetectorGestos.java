package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DetectorGestos implements GestureListener {
	private PantallaJuego lu;
	
	public DetectorGestos(PantallaJuego lumpundform) {
		lu = lumpundform;
	}
	
	static public Vector3 alinearCoordenadas(int x, int y, Camera cam) {
		// Alinear coordenadas del punto del toque con le dirección de la
		// camara
		Vector3 posicion = new Vector3();
		posicion.set(x, y, 0);
		cam.unproject(posicion);
		
		return posicion;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean tap(int x, int y, int count) {
		if (count >= 2) {
			// Alinear coordenadas del punto del toque con le dirección de la
			// camara
			Vector3 pos = alinearCoordenadas(x, y, lu.camara);

			boolean derecha = pos.x > lu.heroe.x ? true : false;
			float movDelta = derecha ? pos.x - lu.heroe.x : -(lu.heroe.x - pos.x);
			
			lu.heroe.x = lu.heroe.destinoX = pos.x;
			lu.heroe.y = pos.y;
			lu.heroe.estado = Personaje.CAYENDO;
			
			lu.camara.translate(movDelta, 0);
		}
		return false;
	}

	@Override
	public boolean longPress(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(int x, int y, int deltaX, int deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer,
			Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {
		// TODO Auto-generated method stub
		return false;
	}

}
