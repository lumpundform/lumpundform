package com.lumpundform.lumpundform;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioHelper;
import com.lumpundform.excepciones.HabilidadInexistenteException;
import com.lumpundform.utilerias.U;

/**
 * Clase que implementa {@link InputProcessor} y {@link GestureListener} para
 * los controles del juego
 * 
 * @author Sergio
 * 
 */
public class ProcesadorEntradaJuego implements GestureListener, InputProcessor {
	private EscenarioHelper escenario;

	public ProcesadorEntradaJuego(EscenarioHelper escenario) {
		this.escenario = escenario;
	}

	// GestureListener

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Vector2 posicion = U.voltearCoordenadas(x, y);
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
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {
		return false;
	}

	// InputProcessor

	@Override
	public boolean keyDown(int keycode) {
		Heroe heroe = escenario.getHeroe();
		if (keycode == Keys.SPACE) {
			try {
				heroe.habilidad("disparar");
			} catch (HabilidadInexistenteException e) {
				U.err(e);
			}
			return true;
		} else if (keycode == Keys.Q) {
			heroe.usarPocion("vida");
		} else if (keycode == Keys.E) {
			heroe.usarPocion("mana");
		} else if (keycode == Keys.N) {
			if(escenario.hayEventoActivado() && escenario.hayEscenaActivada()) {
				if(!escenario.getEscenaActivada().getEscena().getPasoActual().getAccionHablar().getTerminado()){
					escenario.getEscenaActivada().getEscena().getPasoActual().getAccionHablar().terminarAccion();
				}
			}
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
	public boolean mouseMoved(int screenX, int screenY) {
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
	public boolean scrolled(int amount) {
		return false;
	}

}
