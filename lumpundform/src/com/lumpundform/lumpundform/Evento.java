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
	public int limite;

	private int cantidadActual;

	public Evento(Vector2 posicion, TiledObject objeto, EscenarioBase escenario) {
		this.posicion = posicion;
		this.nombre = objeto.name;
		this.tipo = objeto.type;
		this.rango = Float.parseFloat(objeto.properties.get("rango"));
		this.activado = false;
		this.terminado = false;
		this.escenario = escenario;
		if (objeto.properties.containsKey("limite")) {
			this.limite = Integer.parseInt(objeto.properties.get("limite"));
		}

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
					cantidadActual = escenario.getPersonajes().size();
					activado = true;
				} else if (escenario.getPersonajes().size() < (cantidadActual + limite)
						&& activado == true) {
					escenario.agregarActor("humanoide", new Vector2(
							posicion.x - 64, posicion.y), nombre);

				}
				if (escenario.getPersonajes().size() >= (cantidadActual + limite)) {
					terminado = true;
				}
			}
		} catch (ActorNoDefinidoException e) {
			U.err(e);
		}
	}
}