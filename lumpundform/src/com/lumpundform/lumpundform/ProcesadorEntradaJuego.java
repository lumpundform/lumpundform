package com.lumpundform.lumpundform;

import com.badlogic.gdx.Input.Keys;
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
public class ProcesadorEntradaJuego implements GestureListener, InputProcessor {
	private PantallaJuego pantalla;
	private EscenarioHelper escenario;

	public ProcesadorEntradaJuego(PantallaJuego pantalla) {
		this.pantalla = pantalla;
		this.escenario = pantalla.getEscenario();
	}

	// GestureListener

	@Override
	public boolean touchDown(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean tap(int x, int y, int count) {
		Vector2 posicion = U.voltearCoordenadas(pantalla.getCamara(), x, y);
		if (count >= 2) {
			try {
				escenario.getHeroe().habilidad("teletransportar", posicion);
			} catch (HabilidadInexistenteException e) {
				U.err(e);
			}
		}
		return false;
	}

	@Override
	public boolean longPress(int x, int y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY) {
		return false;
	}

	@Override
	public boolean pan(int x, int y, int deltaX, int deltaY) {
		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer,
			Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {
		return false;
	}

	// InputProcessor

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE) {
			try {
				escenario.getHeroe().habilidad("disparar");
			} catch (HabilidadInexistenteException e) {
				U.err(e);
			}
			return true;
		}
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
