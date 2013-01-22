package com.lumpundform.lumpundform;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioHelper;
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
			escenario.getHeroe().habilidad("teletransportar", posicion);
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
		if (keycode == Keys.SPACE) { /* Disparar */
			heroe.habilidad("disparar");
			return true;
		} else if (keycode == Keys.Q) { /* Usar poción vida */
			heroe.usarPocion("vida");
			return true;
		} else if (keycode == Keys.E) { /* Usar poción mana */
			heroe.usarPocion("mana");
			return true;
		} else if (keycode == Keys.N) { /* Avanzar texto en diálogos */
			if (escenario.hayEventoActivado() && escenario.hayEscenaActivada()) {
				if (!escenario.getEscenaActivada().getEscena().getPasoActual().getAccionHablar().getTerminado()) {
					escenario.getEscenaActivada().getEscena().getPasoActual().getAccionHablar().terminarAccion();
					return true;
				}
			}
		} else if (keycode == Keys.A || keycode == Keys.D) { /* Mover al héroe */
			if (heroe.getMovimiento() == 0) {
				if (keycode == Keys.A) {
					heroe.setMovimiento(-1);
				} else {
					heroe.setMovimiento(1);
				}
				return true;
			}
		} else if (keycode >= Keys.NUM_1 && keycode <= Keys.NUM_6) { /* Usar habilidades de interfaz */
			escenario.getInterfazHelper().ejecutarHabilidad(U.numeroConKeycode(keycode));
		} else if (keycode == Keys.BACKSPACE) { /* Toggle líneas de colisión */
			escenario.setDibujarColision(!escenario.isDibujarColision());
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		Heroe heroe = escenario.getHeroe();
		if ((keycode == Keys.A && heroe.getMovimiento() == -1) || (keycode == Keys.D && heroe.getMovimiento() == 1)) {
			heroe.setMovimiento(0);
		}
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
