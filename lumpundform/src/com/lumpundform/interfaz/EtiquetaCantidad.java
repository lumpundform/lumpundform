package com.lumpundform.interfaz;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lumpundform.utilerias.Fuentes;

public class EtiquetaCantidad extends Label {

	public EtiquetaCantidad(CharSequence text, Vector2 posicion) {
		super(text, new LabelStyle(Fuentes.regular(), Fuentes.regular().getColor()));

		getStyle().font.setScale(0.75f);

		setX(posicion.x);
		setY(posicion.y);
	}

}
