package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;

public class Evento {
	public Vector2 posicion;
	public String nombre;
	public String tipo;
	public float rango;
	public Boolean activado;
	public Boolean terminado;
	public EscenarioBase escenario;
	private int cantidadActual;

	public Evento(Vector2 posicion, TiledObject objeto, EscenarioBase escenario) {
		this.posicion = posicion;
		this.nombre = objeto.name;
		this.tipo = objeto.type;
		this.rango = Float.parseFloat(objeto.properties.get("rango"));
		this.activado = false;
		this.terminado = false;
		this.escenario = escenario;
	}

	public void revisarEvento() {
		Heroe heroe = escenario.getHeroe();
		if (heroe.getPosicionCentro().x > (posicion.x - rango)
				&& heroe.getPosicionCentro().x < (posicion.x + rango)) {
			ejecutarEvento();
			U.ds(tipo);
		}

	}

	private void ejecutarEvento() {
		try {
			if (tipo.equals("spawn") && terminado.equals(false)) {
				if (activado == false) {
					cantidadActual = escenario.getActores().size();
					activado = true;
				} else if (escenario.getActores().size() < (cantidadActual + 3)
						&& activado == true) {
					escenario.agregarActor("humanoide", new Vector2(
							posicion.x - 64, posicion.y), nombre);
					
				}
			}
		} catch (ActorNoDefinidoException e) {
			U.err(e);
		}
	}
}