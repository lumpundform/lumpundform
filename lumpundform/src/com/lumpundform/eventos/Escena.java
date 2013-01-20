package com.lumpundform.eventos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor.Direccion;
import com.lumpundform.excepciones.HabilidadInexistenteException;
import com.lumpundform.interfaz.CuadroTexto;
import com.lumpundform.utilerias.U;

public class Escena {

	// Elementos de la escena
	private Array<Paso> pasosEscena = new Array<Paso>();
	public String nombreEscena;
	private int paso = 0;
	public boolean escenaTerminada = false;

	// Cuadros de texto
	CuadroTexto ctDer = new CuadroTexto("der");
	CuadroTexto ctIzq = new CuadroTexto("izq");

	public Escena(Element escena, String nombreEscena) {
		crearPasos(escena.getChildrenByNameRecursively("paso"));
		this.nombreEscena = nombreEscena;
	}

	private void crearPasos(Array<Element> pasos) {
		for (int i = 0; i < pasos.size; i++) {
			pasosEscena.add(new Paso(pasos.get(i)));
		}
	}

	public void ejecutarEscena(Heroe heroe, float delta) {
		revisarPasos(heroe, delta);
	}

	private void revisarPasos(Heroe heroe, float delta) {
		if (paso < pasosEscena.size) {
			revisarAcciones(heroe, delta, pasosEscena.get(paso));
		} else {
			escenaTerminada = true;
		}
	}

	private void revisarAcciones(Heroe heroe, float delta, Paso paso) {
		if (accionesTerminadas(paso)) {
			ejecutarAcciones(heroe, delta, paso);
			// ejecutarAccion(paso.acciones.get(paso.getAccionAEjecutar()).getObjetivo(),
			// paso.acciones.get(paso.getAccionAEjecutar()), heroe, delta,
			// paso);
		} else {
			siguientePaso();
		}
	}

	private void ejecutarAcciones(Heroe heroe, float delta, Paso paso) {
		for (int i = 0; i < paso.acciones.size; i++) {
			U.l("Accion terminado", paso.acciones.get(i).getObjetivo()
					+ paso.acciones.get(i).getTerminado());
			if (!paso.acciones.get(i).getTerminado()) {
				ejecutarAccion(paso.acciones.get(i), heroe, delta);
			}
		}
	}

	private void ejecutarAccion(Accion accion, Heroe heroe, float delta) {
		U.l("accion", accion.getObjetivo());
		if (accion.getObjetivo().equals("hablar"))
			hablar(accion);
		else if (accion.getObjetivo().equals("ir_a"))
			caminar(heroe, accion.getDestino(), delta, accion);
		else if (accion.getObjetivo().equals("teletransportarse"))
			teletransportarse(heroe, accion.getPosicionVector(), accion);
	}

	/*
	 * private void ejecutarAccion(String objetivo, Accion accion, Heroe heroe,
	 * float delta, Paso paso) { if(objetivo.equals("hablar")) { hablar(accion,
	 * paso, accion.getTexto(), accion.getPosicion()); } else if
	 * (objetivo.equals("ir_a")) { caminar(heroe, accion.getDestino(), delta,
	 * paso); } else if (objetivo.equals("teletransportarse")) {
	 * teletransportarse(paso, heroe, accion.getPosicionVector()); } }
	 */
	public Paso getPasoActual() {
		return pasosEscena.get(paso);
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
			accion.terminarAccion();
	}

	@SuppressWarnings("unused")
	private void teletransportarse(Heroe heroe, Vector2 pos, Accion accion) {
		try {
			heroe.habilidad("teletransportar", pos);
			accion.terminarAccion();
		} catch (HabilidadInexistenteException e) {
			e.printStackTrace();
		}
	}
}