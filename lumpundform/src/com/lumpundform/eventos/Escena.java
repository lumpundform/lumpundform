package com.lumpundform.eventos;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.lumpundform.actores.Heroe;
import com.lumpundform.interfaz.CuadroTexto;
import com.lumpundform.utilerias.U;

public class Escena {

	public Element escena;
	public int paso = 0;
	public String nombre;

	public Escena(Element escena, String nombre) {
		this.escena = escena;
		this.nombre = nombre;
	}

	public void ejecutarEscena(Heroe heroe) {
		revisarPaso(heroe);
	}

	private void revisarPaso(Heroe heroe) {
		Array<Element> pasos = escena.getChildrenByNameRecursively("paso");
		if(paso < pasos.size) {
			revisarAccion(pasos.get(paso), heroe);
		}
	}

	private void revisarAccion(Element pasos, Heroe heroe) {
		Array<Element> acciones = pasos.getChildrenByNameRecursively("accion");
		for (int i = 0; i < acciones.size; i++) {
			U.l("evento", acciones.get(i).get("objetivo"));
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