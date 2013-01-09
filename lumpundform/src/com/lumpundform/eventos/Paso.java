package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.utilerias.U;

public class Paso {
	Array<Accion> acciones = new Array<Accion>();

	public Paso(Element paso) {
		crearAcciones(paso.getChildrenByNameRecursively("accion"));
	}

	private void crearAcciones(Array<Element> paso) {
		for (int i = 0; i < paso.size; i++) {
			U.l("paso", paso.get(i));
			acciones.add(new Accion(paso.get(i)));
		}
	}
}