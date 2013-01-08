package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor.Direccion;
import com.lumpundform.interfaz.CuadroTexto;

public class Escena {

	public Element escena;
	public String nombre;
	public int paso = 0;
	
	private int indexAccion	= 0;
	private CuadroTexto ct = new CuadroTexto();

	public Escena(Element escena, String nombre) {
		this.escena = escena;
		this.nombre = nombre;
	}

	public void ejecutarEscena(Heroe heroe, float delta) {
		revisarPasos(heroe, delta);
	}

	private void revisarPasos(Heroe heroe, float delta) {
		Array<Element> pasos = escena.getChildrenByNameRecursively("paso");
		if(paso < pasos.size) {
			revisarAcciones(pasos.get(paso), heroe, delta);
		}
	}
	
	private void ejecutarAccion(String objetivo, Element accion, Heroe heroe, float delta) {
		if(objetivo.equals("hablar")) {
			ct.setTexto(accion.get("texto"));
			hablar(accion);
		} else if (objetivo.equals("ir_a")) {
			caminar(heroe, Integer.parseInt(accion.get("destino")), delta);
		}
	}
	
	private void hablar(Element accion) {
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

	private void revisarAcciones(Element pasos, Heroe heroe, float delta) {
		Array<Element> acciones = pasos.getChildrenByNameRecursively("accion");
		if (acciones.size > indexAccion) {
			ejecutarAccion(acciones.get(indexAccion).get("objetivo"), acciones.get(indexAccion), heroe, delta);
		}
	}
}