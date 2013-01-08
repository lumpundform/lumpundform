package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
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

	public void ejecutarEscena(Heroe heroe) {
		revisarPasos(heroe);
	}

	private void revisarPasos(Heroe heroe) {
		Array<Element> pasos = escena.getChildrenByNameRecursively("paso");
		if(paso < pasos.size) {
			revisarAcciones(pasos.get(paso), heroe);
		}
	}
	
	private void ejecutarAccion(String objetivo, Element accion, Heroe heroe) {
		if(objetivo.equals("hablar")) {
			ct.setTexto(accion.get("texto"));
			hablar(accion);
		} else if (objetivo.equals("ir_a")) {
			caminar(heroe, Integer.parseInt(accion.get("destino")));
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

	private void caminar(Heroe heroe, float destino) {
		heroe.setDestinoX(destino);
		if(heroe.getDestinoX() > heroe.getX()) {
			heroe.setX(heroe.getX() + 5);
		} else {
			indexAccion++;
		}
	}

	private void revisarAcciones(Element pasos, Heroe heroe) {
		Array<Element> acciones = pasos.getChildrenByNameRecursively("accion");
		if (acciones.size > indexAccion) {
			ejecutarAccion(acciones.get(indexAccion).get("objetivo"), acciones.get(indexAccion), heroe);
		}
	}
}