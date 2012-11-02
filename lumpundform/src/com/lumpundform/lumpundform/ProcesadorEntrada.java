package com.lumpundform.lumpundform;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputProcessor;

/**
 * Clase que implementa {@link InputProcessor} y {@link GestureListener} para
 * los controles del juego
 * 
 * @author Sergio
 * 
 */
public class ProcesadorEntrada implements GestureListener, InputProcessor {
	private PantallaJuego pantalla;
	private CamaraJuego camara;
	private Heroe heroe;

	public ProcesadorEntrada(Lumpundform juego) {
		this.pantalla = (PantallaJuego) juego.getScreen();
		this.camara = pantalla.getCamara();
		this.heroe = pantalla.getEscenario().getHeroe();
	}

	// GestureListener

	@Override
	public boolean touchDown(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(int x, int y, int count) {
		if (count >= 2) {
			heroe.teletransportar(U.voltearCoordenadas(camara, x, y), pantalla
					.getEscenario().getPiso());
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

	// InputProcessor

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
