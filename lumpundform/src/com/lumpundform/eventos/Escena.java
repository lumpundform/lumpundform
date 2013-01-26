package com.lumpundform.eventos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor.Direccion;
import com.lumpundform.interfaz.CuadroTexto;

public class Escena {

	// Elementos de la escena
	private Array<Paso> pasos = new Array<Paso>();
	private String nombre;
	private int paso = 0;
	private boolean terminado = false;

	// Cuadros de texto
	CuadroTexto ctDer = new CuadroTexto("der");
	CuadroTexto ctIzq = new CuadroTexto("izq");

	public Escena(Element escena, String nombreEscena) {
		crearPasos(escena.getChildrenByNameRecursively("paso"));
		this.nombre = nombreEscena;
	}

	private void crearPasos(Array<Element> pasos) {
		for (int i = 0; i < pasos.size; i++) {
			this.pasos.add(new Paso(pasos.get(i)));
		}
	}

	public void ejecutarEscena(Heroe heroe, float delta) {
		revisarPasos(heroe, delta);
	}

	private void revisarPasos(Heroe heroe, float delta) {
		if (paso < pasos.size) {
			revisarAcciones(heroe, delta, pasos.get(paso));
		} else {
			terminado = true;
		}
	}

	private void revisarAcciones(Heroe heroe, float delta, Paso paso) {
		if (accionesTerminadas(paso)) {
			ejecutarAcciones(heroe, delta, paso);
		} else {
			siguientePaso();
		}
	}

	private void ejecutarAcciones(Heroe heroe, float delta, Paso paso) {
		for (int i = 0; i < paso.acciones.size; i++) {
			if (!paso.acciones.get(i).getTerminado()) {
				ejecutarAccion(paso.acciones.get(i), heroe, delta);
			}
		}
	}

	private void ejecutarAccion(Accion accion, Heroe heroe, float delta) {
		if (accion.getObjetivo().equals("hablar"))
			hablar(accion);
		else if (accion.getObjetivo().equals("ir_a"))
			caminar(heroe, accion.getDestino(), delta, accion);
		else if (accion.getObjetivo().equals("teletransportarse"))
			teletransportarse(heroe, accion.getPosicionVector(), accion);
	}

	private void siguientePaso() {
		this.paso++;
	}

	private boolean accionesTerminadas(Paso paso) {
		for (int i = 0; i < paso.acciones.size; i++) {
			if (!paso.acciones.get(i).getTerminado())
				return true;
		}
		return false;
	}

	// Acciones para las escenas

	private void hablar(Accion accion) {
		if (!accion.getTerminado()) {
			ctIzq.setTexto(accion.getTexto());
			ctIzq.draw();
		}
	}

	private void caminar(Heroe heroe, float destino, float delta, Accion accion) {
		if (!accion.getTerminado()) {
			heroe.setDestinoX(destino);
			heroe.setDireccionDestinoX(Direccion.DERECHA);
			heroe.moverDestino(delta);
		}
		if ((heroe.getDestinoX() > heroe.getX() && heroe.getDireccionDestinoX() == Direccion.IZQUIERDA)
				|| (heroe.getDestinoX() < heroe.getX() && heroe
						.getDireccionDestinoX() == Direccion.DERECHA))
			accion.terminar();
	}

	private void teletransportarse(Heroe heroe, Vector2 pos, Accion accion) {
		heroe.habilidad("teletransportar", pos);
		accion.terminar();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public boolean getTerminada() {
		return terminado;
	}

	public void continuarConversacion() {
		Paso pasoActual = getPasoActual();
		if(pasoActual != null && pasoActual.tieneAccionHablar())
			pasoActual.terminarAccionHablar();
	}
	
	private Paso getPasoActual() {
		if(paso < pasos.size)
			return pasos.get(paso);
		return null;
	}
}