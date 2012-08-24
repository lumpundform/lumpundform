package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class ProcesadorEntrada implements InputProcessor {
	private PantallaJuego lu;
	
	public ProcesadorEntrada(PantallaJuego lumpundform) {
		lu = lumpundform;
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
