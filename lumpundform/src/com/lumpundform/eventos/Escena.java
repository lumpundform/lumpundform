package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.interfaz.CuadroTexto;
import com.lumpundform.utilerias.U;

public class Escena {

	public Element escena;
	public int paso = 0;
	private int indexAccion = 0;
	public String nombre;
	
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
			U.l("ir_a",accion.get("destino"));
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

	private void revisarAcciones(Element pasos, Heroe heroe) {
		Array<Element> acciones = pasos.getChildrenByNameRecursively("accion");
		if (acciones.size > indexAccion) {
			ejecutarAccion(acciones.get(indexAccion).get("objetivo"), acciones.get(indexAccion), heroe);
		}
		//ct.draw(newstr);
		/*
		for (int i = 0; i < eventos.size; i++) {
			Element evento = eventos.get(i);
			// U.l("escena", evento.get("destino"));
			if(evento.get("objetivo").equals("hablar")) {
				String texto = evento.get("texto");
				CuadroTexto ct = new CuadroTexto(texto);
				int length = ct.texto.length();
				if(length > ct.index) {
					ct.newstr += ct.texto.substring(ct.index, ++ct.index);
				} else {
					if(paso < eventos.size) {
						paso++;
					}
				}
				ct.draw();
				U.l("objetivo", evento.get("objetivo"));
				heroe.setDestinoX(Float.parseFloat(evento.get("destino")));
				if(heroe.getDestinoX() > heroe.getX()) {
					heroe.setX(heroe.getX() + 5);
				}
			}
		}*/
	}
}