package com.lumpundform.eventos;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.ObjetoActor.Direccion;
import com.lumpundform.actores.Personaje;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.interfaz.CuadroTexto;

public class Escena {

	// Elementos de la escena
	private Array<Paso> pasos = new Array<Paso>();
	private String nombre;
	private int paso = 0;
	private boolean terminado = false;

	private EscenarioBase escenario;

	// Cuadros de texto
	Map<String, CuadroTexto> cuadrosTexto = new HashMap<String, CuadroTexto>();

	public Escena(Element escena, String nombreEscena) {
		crearPasos(escena.getChildrenByNameRecursively("paso"));
		this.nombre = nombreEscena;
	}

	private void crearPasos(Array<Element> pasos) {
		for (int i = 0; i < pasos.size; i++) {
			this.pasos.add(new Paso(pasos.get(i)));
		}
	}

	public void ejecutarEscena(EscenarioBase escenario) {
		this.escenario = escenario;
		revisarPasos();
	}

	private void revisarPasos() {
		if (paso < pasos.size) {
			revisarAcciones(pasos.get(paso));
		} else {
			terminado = true;
		}
	}

	private void revisarAcciones(Paso paso) {
		if (accionesTerminadas(paso)) {
			ejecutarAcciones(paso);
		} else {
			siguientePaso();
		}
	}

	private void ejecutarAcciones(Paso paso) {
		for (int i = 0; i < paso.acciones.size; i++) {
			if (!paso.acciones.get(i).getTerminado()) {
				ejecutarAccion(paso.acciones.get(i), (Personaje) escenario.getActor(paso.acciones.get(i)
						.getNombreActor(), paso.acciones.get(i).getPosicion()));
			}
		}
	}

	private void ejecutarAccion(Accion accion, Personaje personaje) {
		if (accion.getObjetivo().equals("hablar"))
			hablar(accion, personaje);
		else if (accion.getObjetivo().equals("ir_a"))
			caminar(accion.getDestino(), accion, personaje);
		else if (accion.getObjetivo().equals("teletransportarse"))
			teletransportarse(accion.getPosicion(), accion, personaje);
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

	private void hablar(Accion accion, Personaje personaje) {
		if (!accion.getTerminado()) {
			if (!cuadrosTexto.containsKey(personaje.getName())) {
				CuadroTexto tempCT = new CuadroTexto(accion.getPosicionTexto());
				tempCT.setTexto(accion.getTexto());
				cuadrosTexto.put(personaje.getName(), tempCT);
			} else {
				cuadrosTexto.get(personaje.getName()).setTexto(accion.getTexto());
			}
		}
		dibujarTexto(personaje.getName());
	}

	private void caminar(float destino, Accion accion, Personaje personaje) {

		if (!accion.getTerminado()) {
			personaje.setDestinoX(destino);
			personaje.setDireccionDestinoX(Direccion.DERECHA);
			personaje.moverDestino();
		}
		if ((personaje.getDestinoX() > personaje.getX() && personaje.getDireccionDestinoX() == Direccion.IZQUIERDA)
				|| (personaje.getDestinoX() < personaje.getX() && personaje.getDireccionDestinoX() == Direccion.DERECHA))
			accion.terminar();
	}

	private void teletransportarse(Vector2 pos, Accion accion, Personaje personaje) {
		personaje.habilidad("teletransportar", pos);
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
		if (pasoActual != null && pasoActual.tieneAccionHablar()) {
			if (accionHablarTermino()) {
				siguienteCuadroTexto();
				pasoActual.terminarAccionHablar();
			} else if(!accionHablarTermino()) {
				completarTexto();
			}
		}
	}

	private Paso getPasoActual() {
		if (paso < pasos.size)
			return pasos.get(paso);
		return null;
	}

	private boolean accionHablarTermino() {
		for (CuadroTexto cuadroTexto : cuadrosTexto.values()) {
			if (!cuadroTexto.terminado())
				return false;
		}
		return true;
	}

	private void siguienteCuadroTexto() {
		for (CuadroTexto cuadroTexto : cuadrosTexto.values()) {
			cuadroTexto.continuar();
		}
	}

	private void dibujarTexto(String nombrePersonaje) {
		CuadroTexto cuadroTexto = cuadrosTexto.get(nombrePersonaje);
		cuadroTexto.draw();
	}
	
	private void completarTexto() {
		for (CuadroTexto cuadroTexto : cuadrosTexto.values()) {
			cuadroTexto.completar();
		}
	}
}