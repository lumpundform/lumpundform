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

	public boolean tieneAccionHablar() {
		for (int i = 0; i < acciones.size; i++) {
			Accion accion = acciones.get(i);
			if (accion.getObjetivo().equals("hablar") && !accion.getTerminado()) {
				return true;
			}
		}
		return false;
	}

	public void terminarAccionHablar() {
		Array<Accion> accionesHablar = getAccionesHablar();
		for (Accion accion : accionesHablar) {
			accion.terminar();
		}
	}

	private Array<Accion> getAccionesHablar() {
		Array<Accion> acciones = new Array<Accion>();
		for (Accion accion : this.acciones) {
			if(accion.getObjetivo().equals("hablar"))
				acciones.add(accion);
		}
		return acciones;
	}
}