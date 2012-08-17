package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DetectorGestos implements GestureListener, InputProcessor {
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
		Vector3 pos = alinearCoordenadas(x, y, lu.camara);

		lu.heroe.destinoX = pos.x;
		lu.heroe.estado = Personaje.MOVIMIENTO;
		
		return false;
	}

	@Override
	public boolean tap(int x, int y, int count) {
		if (count >= 2) {
			// Alinear coordenadas del punto del toque con le dirección de la
			// camara
			Vector3 pos = alinearCoordenadas(x, y, lu.camara);
			
			lu.heroe.x = lu.heroe.destinoX = pos.x;
			lu.heroe.y = pos.y;
			lu.heroe.estado = Personaje.CAYENDO;
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

	@Override
	public boolean keyDown(int keycode) {
		// Mueve al personaje a la izquierda (A) o derecha (D)
		if (keycode == Keys.A || keycode == Keys.D) {
			float velocidadDelta = (lu.heroe.velocidad * Gdx.graphics
					.getDeltaTime());
			if (keycode == Keys.A) {
				lu.heroe.destinoX = lu.heroe.x - velocidadDelta;
			}
			if (keycode == Keys.D) {
				lu.heroe.destinoX = lu.heroe.x + velocidadDelta;
			}
			lu.heroe.estado = Personaje.MOVIMIENTO;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
