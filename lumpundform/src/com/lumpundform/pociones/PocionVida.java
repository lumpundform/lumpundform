package com.lumpundform.pociones;

import com.badlogic.gdx.math.Vector2;

public class PocionVida extends PocionBase {
	public static float cantidad = 20.0f;

	public PocionVida(Vector2 posicion) {
		super(posicion, "vida");
	}

}
