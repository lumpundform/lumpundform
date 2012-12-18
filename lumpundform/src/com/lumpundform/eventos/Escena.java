package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.interfaz.CuadroTexto;

public class Escena {
	
	private Element escena;
	private int accionEjecutada = 0;
	private int index = 0;
	private String newstr = "";
	public CuadroTexto ct = new CuadroTexto();
	
	public Escena(Element escena) {
		this.escena = escena;
	}
	
	public void ejecutarEscena(Heroe heroe) {
		revisarAccion(accionEjecutada, heroe);
	}
	
	private void revisarAccion(int accionEjecutada, Heroe heroe) {
		Array<Element> acciones = escena.getChildrenByName("accion");
		if(accionEjecutada < acciones.size) {
			revisarEscena(acciones.get(accionEjecutada), heroe);
		}
	}
	private void revisarEscena(Element acciones, Heroe heroe) {
		Array<Element> eventos = acciones.getChildrenByNameRecursively("evento");
		for (int i = 0; i < eventos.size; i++) {
			Element evento = eventos.get(i);
			// U.l("escena", evento.get("destino"));
			if(evento.get("objetivo").equals("hablar")) {
				String str = evento.get("texto");
				int length = str.length();
				if(length > index) {
					newstr += str.substring(index, ++index);
				}
				ct.draw(newstr);
				/*
				U.l("objetivo", evento.get("objetivo"));
				heroe.setDestinoX(Float.parseFloat(evento.get("destino")));
				if(heroe.getDestinoX() > heroe.getX()) {
					heroe.setX(heroe.getX() + 5);
				}
				*/
			}
		}
	}
}