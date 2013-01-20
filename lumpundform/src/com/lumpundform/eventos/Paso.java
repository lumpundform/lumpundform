package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Paso {
	Array<Accion> acciones = new Array<Accion>();
	private int accionAEjecutar = 0;

	public Paso(Element paso) {
		crearAcciones(paso.getChildrenByNameRecursively("accion"));
	}

	private void crearAcciones(Array<Element> paso) {
		for (int i = 0; i < paso.size; i++) {
			acciones.add(new Accion(paso.get(i)));
		}
	}

	public int getAccionAEjecutar() {
		return accionAEjecutar;
	}

	public void siguienteAccion() {
		accionAEjecutar++;
	}

	public Accion getAccionHablar() {
		for (int i = 0; i < acciones.size; i++) {
			Accion accion = acciones.get(i);
			if (accion.objetivo.equals("hablar")) {
				return accion;
			}
		}
		return null;
	}
}