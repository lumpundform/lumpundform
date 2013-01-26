package com.lumpundform.eventos;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.lumpundform.CamaraJuego;

public class Evento {
	private Vector2 posicion;
	private String nombre;
	private String tipo;
	private float rango;
	private Boolean activado = false;
	private Boolean terminado = false;
	private EscenarioBase escenario;
	private int limite;

	// Eventos con tiempo límite.
	private float duracion;
	private float tiempoTranscurrido = 0.0f;

	// Cantidad de personajes por cada evento.
	private int personajesCreados = 0;
	private int personajesMatados = 0;

	// Escena
	private Escena escena;

	public Evento(Vector2 posicion, TiledObject objeto, EscenarioBase escenario) {
		this.posicion = posicion;
		this.nombre = objeto.name;
		this.tipo = objeto.type;
		this.escenario = escenario;

		if (objeto.properties.containsKey("rango"))
			this.rango = Float.parseFloat(objeto.properties.get("rango"));
		if (objeto.properties.containsKey("limite"))
			this.limite = Integer.parseInt(objeto.properties.get("limite"));
		if (objeto.properties.containsKey("duracion"))
			this.duracion = Float.parseFloat(objeto.properties.get("duracion"));

		// Crear escena
		if (objeto.type.equals("escena"))
			escena = escenario.getEscena(objeto.properties.get("escena"));
	}

	public void revisarEvento(CamaraJuego camara, Heroe heroe, float delta) {
		if (!activado || !terminado) {
			ejecutarEvento(camara, heroe, delta);
		}

	}

	private void ejecutarEvento(CamaraJuego camara, Heroe heroe, float delta) {
		if (tipo.equals("spawn")) {
			if (activado == false && heroe.getX() > (posicion.x - rango) && heroe.getX() < (posicion.x + rango)) {
				activado = true;
			} else if (limite > personajesCreados && activado.equals(true)) {
				escenario.agregarActor("enemigo", new Vector2(posicion.x - 64, posicion.y), getNombre());
				personajesCreados += 1;
			}
			if (limite <= personajesCreados) {
				terminado = true;
			}
		} else if (tipo.equals("coliseo")) {
			if (activado == false && heroe.getX() > (posicion.x - rango) && heroe.getX() < (posicion.x + rango)) {
				activado = true;
				camara.setBloqueada(true);
			} else if (limite > personajesCreados && activado.equals(true)) {
				escenario.agregarActor("enemigo", new Vector2(posicion.x - 64, posicion.y), getNombre());
				personajesCreados += 1;
			} else if (limite == personajesMatados) {
				camara.setBloqueada(false);
				terminado = true;
			}
		} else if (tipo.equals("clima")) {
			if (activado == false && heroe.getX() > (posicion.x - rango) && heroe.getX() < (posicion.x + rango)) {
				activado = true;
			} else if (activado == true && duracion > tiempoTranscurrido) {
				// ejecutar lo que haga el evento
				tiempoTranscurrido += delta;
			} else if (activado == true && duracion <= tiempoTranscurrido) {
				terminado = true;
			}
		} else if (tipo.equals("escena")) {
			if (activado == false && heroe.getX() > (posicion.x - rango) && heroe.getX() < (posicion.x + rango)) {
				activado = true;
			} else if (activado == true && terminado == false && escena.getTerminada() == false) {
				escena.ejecutarEscena(heroe, delta);
			} else if (activado == true && escena.getTerminada() == true) {
				terminado = true;
			}
		}
	}

	public void matarPersonaje() {
		personajesMatados += 1;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Escena getEscena() {
		return escena;
	}

	public boolean getActivado() {
		return activado;
	}

	public boolean getTerminado() {
		return terminado;
	}

	public String getTipoEvento() {
		return tipo;
	}

	public boolean esEscenaActivada() {
		return (activado && !terminado && tipo.equals("escena"));
	}
	
	public void continuarConversacionEnEscena() {
		escena.continuarConversacion();
	}
}