package com.lumpundform.eventos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor.Direccion;
import com.lumpundform.excepciones.HabilidadInexistenteException;
import com.lumpundform.interfaz.CuadroTexto;

public class Escena {

	public Array<Paso> pasosEscena = new Array<Paso>();
	public String nombre;
	public int paso = 0;

	private int indexAccion	= 0;

	// Cuadro de texto
	private CuadroTexto ct = new CuadroTexto();

	public Escena(Element escena, String nombre) {
		crearPasos(escena.getChildrenByNameRecursively("paso"));
		this.nombre = nombre;
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
			revisarAcciones(pasosEscena.get(paso), heroe, delta);
		}
	}

	private void ejecutarAccion(String objetivo, Accion accion, Heroe heroe, float delta) {
		if(objetivo.equals("hablar")) {
			ct.setTexto(accion.getTexto());
			hablar(accion);
		} else if (objetivo.equals("ir_a")) {
			caminar(heroe, accion.getDestino(), delta);
		} else if (objetivo.equals("teletransportarse")) {
			teletransportarse(heroe, accion.getPosicionVector());
		}
	}

	private void hablar(Accion accion) {
		Boolean hablar = ct.drawCt();
		if(!hablar) {
			ct.index = 0;
			ct.newstr = "";
			indexAccion++;
		}
	}

	private void caminar(Heroe heroe, float destino, float delta) {
		heroe.setDestinoX(destino);
		heroe.setDireccionDestinoX(Direccion.DERECHA);
		heroe.moverDestino(delta);
		if((heroe.getDestinoX() > heroe.getX() && heroe.getDireccionDestinoX() == Direccion.IZQUIERDA) ||
				(heroe.getDestinoX() < heroe.getX() && heroe.getDireccionDestinoX() == Direccion.DERECHA)) {
			indexAccion++;
		}
	}

	@SuppressWarnings("unused")
	private void teletransportarse(Heroe heroe, Vector2 pos) {
		try {
			heroe.habilidad("teletransportar", pos);
			indexAccion++;
		} catch (HabilidadInexistenteException e) {
			e.printStackTrace();
		}
	}

	private void revisarAcciones(Paso paso, Heroe heroe, float delta) {
		if (indexAccion < paso.acciones.size) {
			ejecutarAccion(paso.acciones.get(indexAccion).getObjetivo(),
					paso.acciones.get(indexAccion), heroe, delta);
		}
	}
}