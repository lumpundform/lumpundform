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
	
	// Eventos con tiempo límite.
	public float duracion;
	public float tiempoTranscurrido = 0.0f;

	// Cantidad de personajes por cada evento.
	public int personajesCreados = 0;
	public int personajesMatados = 0;

	public Evento(Vector2 posicion, TiledObject objeto, EscenarioBase escenario) {
		this.posicion = posicion;
		this.nombre = objeto.name;
		this.tipo = objeto.type;
		this.activado = false;
		this.terminado = false;
		this.escenario = escenario;
		
		if (objeto.properties.containsKey("rango"))
			this.rango = Float.parseFloat(objeto.properties.get("rango"));
		if (objeto.properties.containsKey("limite"))
			this.limite = Integer.parseInt(objeto.properties.get("limite"));
		if (objeto.properties.containsKey("duracion"))
			this.duracion = Float.parseFloat(objeto.properties.get("duracion"));
	}

	public void revisarEvento(CamaraJuego camara, Heroe heroe, float delta) {
		if (!activado || !terminado) {
			ejecutarEvento(camara, heroe, delta);
		}

	}

	private void ejecutarEvento(CamaraJuego camara, Heroe heroe, float delta) {
		try {
			if (tipo.equals("spawn")) {
				if (activado == false && heroe.x > (posicion.x - rango) && heroe.x < (posicion.x + rango)) {
					activado = true;
				} else if (limite > personajesCreados && activado.equals(true)) {
					escenario.agregarActor("humanoide", new Vector2(
							posicion.x - 64, posicion.y), nombre);
					personajesCreados += 1;
				}
				if (limite <= personajesCreados) {
					terminado = true;
				}
			} else if (tipo.equals("coliseo")) {
				if (activado == false && heroe.x > (posicion.x - rango) && heroe.x < (posicion.x + rango)) {
					activado = true;
					camara.bloqueada = true;
				} else if (limite > personajesCreados && activado.equals(true)) {
					escenario.agregarActor("humanoide", new Vector2(
							posicion.x - 64, posicion.y), nombre);
					personajesCreados += 1;
				} else if (limite == personajesMatados) {
					camara.bloqueada = false;
					terminado = true;
				}
			} else if (tipo.equals("clima")) {
				if (activado == false && heroe.x > (posicion.x - rango) && heroe.x < (posicion.x + rango)) {
					activado = true;
				} else if (activado == true && duracion > tiempoTranscurrido) {
					// ejecutar lo que haga el evento
					tiempoTranscurrido += delta;
				} else if (activado == true && duracion <= tiempoTranscurrido) {
					terminado = true;
				}
			} 
		} catch (ActorNoDefinidoException e) {
			U.err(e);
		}
	}
	
	public void matarPersonaje() {
		personajesMatados += 1;
	}
}