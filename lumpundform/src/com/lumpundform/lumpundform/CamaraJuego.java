package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CamaraJuego extends OrthographicCamera {
	public Vector2 posicionOrigen = new Vector2();
	
	public CamaraJuego() {
		super();
		
		setPosicion(position.x, position.y);
	}
	
	public void setPosicion(float x, float y) {
		position.x = x;
		position.y = y;
		posicionOrigen.x = x - viewportWidth / 2;
		posicionOrigen.y = y - viewportHeight / 2;
	}
	
	public void setPosicionOrigen(float x, float y) {
		posicionOrigen.x = x;
		posicionOrigen.y = y;
		position.x = x + viewportWidth / 2;
		position.y = y + viewportHeight / 2;
	}
	
	public Vector2 getPosicionOrigen() {
		return posicionOrigen;
	}
}
