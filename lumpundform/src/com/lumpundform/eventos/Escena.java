package com.lumpundform.eventos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor.Direccion;
import com.lumpundform.excepciones.HabilidadInexistenteException;
import com.lumpundform.interfaz.CuadroTexto;

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
		if(paso < pasosEscena.size) {
			revisarAcciones(heroe, delta, pasosEscena.get(paso));
		} else {
			escenaTerminada = true;
		}
	}

	private void revisarAcciones(Heroe heroe, float delta, Paso paso) {
		if (paso.getAccionAEjecutar() < paso.acciones.size) {
			ejecutarAccion(paso.acciones.get(paso.getAccionAEjecutar()).getObjetivo(),
					paso.acciones.get(paso.getAccionAEjecutar()), heroe, delta, paso);
		} else {
			this.paso++;
		}
	}

	private void ejecutarAccion(String objetivo, Accion accion, Heroe heroe, float delta, Paso paso) {
		if(objetivo.equals("hablar")) {
			hablar(accion, paso, accion.getTexto(), accion.getPosicion());
		} else if (objetivo.equals("ir_a")) {
			caminar(heroe, accion.getDestino(), delta, paso);
		} else if (objetivo.equals("teletransportarse")) {
			teletransportarse(paso, heroe, accion.getPosicionVector());
		}
	}

	// Acciones para las escenas

	private void hablar(Accion accion, Paso paso, String texto, String posicion) {
		ctIzq.setTexto(texto);
		ctIzq.draw();
	}

	private void caminar(Heroe heroe, float destino, float delta, Paso paso) {
		heroe.setDestinoX(destino);
		heroe.setDireccionDestinoX(Direccion.DERECHA);
		heroe.moverDestino(delta);
		if((heroe.getDestinoX() > heroe.getX() && heroe.getDireccionDestinoX() == Direccion.IZQUIERDA) ||
				(heroe.getDestinoX() < heroe.getX() && heroe.getDireccionDestinoX() == Direccion.DERECHA)) {
			paso.siguienteAccion();
		}
	}

	@SuppressWarnings("unused")
	private void teletransportarse(Paso paso, Heroe heroe, Vector2 pos) {
		try {
			heroe.habilidad("teletransportar", pos);
			paso.siguienteAccion();
		} catch (HabilidadInexistenteException e) {
			e.printStackTrace();
		}
	}
}