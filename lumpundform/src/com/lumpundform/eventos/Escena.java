package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.utilerias.U;

public class Escena {
	
	private Element escena;
	private int accionEjecutada = 0;
	private int index = 0;
	private String newstr = "";
	
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
		U.ds(newstr, 50, 50, 0.5f);
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
				/*U.l("objetivo", evento.get("objetivo"));
				heroe.destinoX = Float.parseFloat(evento.get("destino"));
				if(heroe.destinoX > heroe.x) {
					heroe.x += 5;
				}*/
			}
		}
	}
}