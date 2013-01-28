package com.lumpundform.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.lumpundform.lumpundform.Lumpundform;
import com.lumpundform.pantallas.PantallaJuego;

public class ProcesadorEntradaGeneral implements InputProcessor {
	private Lumpundform juego;

	public ProcesadorEntradaGeneral(Lumpundform juego) {
		this.juego = juego;
	}

	@Override
	public boolean keyDown(int keycode) {
		PantallaJuego pantallaJuego = (PantallaJuego) juego.getScreen();
		if (pantallaJuego.isHeroeMuerto() || (keycode == Keys.ENTER && Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))) {
			juego.reset();
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
