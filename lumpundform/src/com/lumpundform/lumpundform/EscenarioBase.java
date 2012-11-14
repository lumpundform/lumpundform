package com.lumpundform.lumpundform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/**
 * La base para los escenarios, extiende a {@link Stage} y agrega funciones y
 * valores específicos del juego
 * 
 * @author Sergio
 * 
 */
public class EscenarioBase extends Stage {
	public Poligono piso;

	public Array<Evento> eventos;

	public EscenarioBase(float width, float height, boolean stretch,
			SpriteBatch batch) {
		super(width, height, stretch, batch);
	}

	/**
	 * Dibuja las líneas de colisión del piso del escenario y de todos los
	 * {@link ObjetoActor}es que se encuentran en el escenario
	 */
	public void dibujarLineasColision(CamaraJuego camara) {
		List<Actor> actores = getActors();

		for (int i = 0; i < actores.size(); i++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			U.dibujarLineasColision(actor.getHitbox(), camara);
		}

		U.dibujarLineasColision(piso, camara);
	}

	/**
	 * Cambia el estado del {@link Heroe} si este está colisionando con algún
	 * otro {@link ObjetoActor} del escenario
	 */
	public void colisionActores() {
		Heroe heroe = (Heroe) findActor("heroe");
		List<Actor> actores = getPersonajes();

		for (int i = 0; i < actores.size(); i++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			if (actor.name != "heroe"
					&& heroe.getHitbox().estaColisionando(actor.getHitbox())) {
				heroe.colisionActores = true;
				break;
			} else if (actor.name != "heroe") {
				heroe.colisionActores = false;
			}
		}
	}

	public void colisionAtaques() {
		List<Actor> ataques = getAtaques();
		List<Actor> personajes = getPersonajes();

		for (int i = 0; i < ataques.size(); i++) {
			Ataque ataque = (Ataque) ataques.get(i);
			for (int j = 0; j < personajes.size(); j++) {
				Personaje personaje = (Personaje) personajes.get(j);
				if (!personaje.name.equals("heroe")
						&& personaje.getHitbox().estaColisionando(
								ataque.getHitbox())) {
					if (ataque.haceDano) {
						personaje.quitarVida(ataque.dano, getEvento(personaje.perteneceAEvento));
					}
					ataque.destruir();
				}
			}
		}
	}

	/**
	 * Detecta la colisión de todos los {@link ObjetoActor}es con el piso
	 */
	public void colisionPiso() {
		List<Actor> actores = getPersonajes();
		Map<String, Boolean> caidaLibre = new HashMap<String, Boolean>();

		for (int i = 0; i < actores.size(); i++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			caidaLibre.put(actor.name, false);

			// Datos para cuando va hacia derecha o izquierda
			String puntoColision, puntoColisionTemp;
			String direccionDiagonalDer, direccionDiagonalIzq, direccionLinea;
			if (actor.derecha()) {
				puntoColision = "inf-der";
				puntoColisionTemp = "inf-izq";
				direccionDiagonalDer = "abajo";
				direccionDiagonalIzq = "arriba";
				direccionLinea = "x";
			} else {
				puntoColision = "inf-izq";
				puntoColisionTemp = "inf-der";
				direccionDiagonalDer = "arriba";
				direccionDiagonalIzq = "abajo";
				direccionLinea = "-x";
			}

			// Revisa si el actor está en caída libre o si está colisionando
			// alguna de las esquinas de su hitbox para inicializar variables
			Vector2 p = null;
			Float altura = null;
			String direccionDiagonal = null;
			if (!piso.estaColisionando(actor.getSensor("inf-izq"))
					&& !piso.estaColisionando(actor.getSensor("inf-der"))) {
				caidaLibre.put(actor.name, true);

				Vector2 puntoTemp = actor.getSensor(puntoColisionTemp);
				p = new Vector2(puntoTemp.x, puntoTemp.y - 10);
				altura = actor.y;
				direccionDiagonal = direccionDiagonalDer;
			} else if (piso.estaColisionando(actor.getSensor(puntoColision))) {
				p = actor.getSensor(puntoColision);
				altura = actor.y + 10;
				direccionDiagonal = direccionDiagonalIzq;
			}

			// Saca la línea en la cual se va a posicionar el actor si es
			// necesario
			if (p != null) {
				Linea l = piso.linea("arriba", p);

				// Posiciona al actor sobre la línea si la linea tiene una
				// pendiente menor a 1
				if (l != null
						&& l.pendiente() != null
						&& l.pendiente() <= 1.0001d
						&& l.yEnX(p) <= altura
						&& (l.direccionDiagonal() == direccionDiagonal || l
								.direccionLinea() == direccionLinea)) {
					caidaLibre.put(actor.name, false);
					actor.y = l.yEnX(p);
				}
			}

			// Cambia el estado colisión del actor, asi como teletransportar del
			// héroe
			if (caidaLibre.get(actor.name)) {
				actor.colisionPiso = false;
				actor.y -= 5;
			} else {
				actor.colisionPiso = true;
				if (actor.name == "heroe") {
					actor.teletransportar = false;
				}
			}
		}
	}

	/**
	 * Limita las posiciones de los {@link ObjetoActor}es del escenario para que
	 * no se salgan del mismo
	 */
	public void acomodarActores(float width) {
		List<Actor> actores = getPersonajes();

		for (int i = 0; i < actores.size(); i++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			if (actor.getSensor("inf-izq").x < 0)
				actor.setSensorX("inf-izq", 0.0f);
			if (actor.getSensor("inf-der").x > width)
				actor.setSensorX("inf-izq", (width - actor.getHitbox()
						.getAncho()));

			// Detecta colisión con paredes
			Vector2 pc = null;
			String lineaLateral;
			Double yPunto = null;
			if (actor.derecha()) {
				pc = actor.getSensor("inf-der");
				lineaLateral = "izquierda";
				yPunto = Math.floor(pc.y);
			} else {
				pc = actor.getSensor("inf-izq");
				lineaLateral = "derecha";
				yPunto = Math.floor(pc.y) + 10.0f;
			}

			if (piso.estaColisionando(pc)
					&& piso.linea("arriba", pc).esHorizontal()
					&& piso.linea(lineaLateral, pc).esVertical()
					&& yPunto < Math.floor(piso.linea("arriba", pc).yEnX(pc))) {
				Linea linea = piso.linea(lineaLateral, pc);
				if (linea != null) {
					Float xLinea = null;
					if (lineaLateral == "izquierda") {
						xLinea = linea.xEnY(pc) - actor.getHitbox().getAncho()
								- 1;
					} else {
						xLinea = linea.xEnY(pc) + 1;
					}
					actor.setSensorX("inf-izq", xLinea);
				}
			}
		}
	}

	public void cargarEventos(TiledObjectGroup tog, CamaraJuego camara) {
		eventos = new Array<Evento>();
		for (int i = 0; i < tog.objects.size(); i++) {
			TiledObject to = tog.objects.get(i);
			eventos.add(new Evento(U.voltearCoordenadas(camara, to.x, to.y),
					to, this));
		}
	}

	public void revisarEventos(CamaraJuego camara, float delta) {
		for (int i = 0; i < eventos.size; i++) {
			eventos.get(i).revisarEvento(camara, getHeroe(), delta);
		}
	}

	public void agregarActor(String tipo, Vector2 posicion)
			throws ActorNoDefinidoException {
		agregarActor(tipo, posicion, "");
	}
	
	public void agregarActor(String tipo, Vector2 posicion, String evento)
			throws ActorNoDefinidoException {
		ObjetoActor actor;
		if (tipo == "heroe") {
			actor = new Heroe("heroe", posicion);
		} else if (tipo == "humanoide") {
			actor = new Humanoide("amigo", posicion);
		} else {
			throw new ActorNoDefinidoException("El Actor " + tipo
					+ " no esta definido");
		}
		actor.perteneceAEvento = evento;
		addActor(actor);
	}

	/**
	 * Regresa al {@link Heroe} del escenario
	 * 
	 * @return El héroe
	 */
	public Heroe getHeroe() {
		return (Heroe) findActor("heroe");
	}

	public List<Actor> getPersonajes() {
		return getActores(Personaje.class);
	}

	private List<Actor> getAtaques() {
		return getActores(Ataque.class);
	}
	
	private List<Actor> getActores(Class<?> clase) {
		Iterator<Actor> i = getActors().iterator();
		List<Actor> actores = new ArrayList<Actor>();
		while (i.hasNext()) {
			Actor actor = i.next();
			if (clase.isInstance(actor)) {
				actores.add(actor);
			}
		}
		return actores;
	}
	
	public void destruirAtaques(CamaraJuego camara) {
		for(int i = 0; i < getAtaques().size(); i++) {
			Actor ataque = getAtaques().get(i);
			if ((ataque.x + ataque.width) < camara.getPosicionOrigen().x || ataque.x > (camara.getPosicionOrigen().x + camara.viewportWidth)) {
				ataque.remove();
			}
		}
	}
	
	private Evento getEvento(String nombreEvento) {
		Evento evento = null;
		for(int i = 0; i < eventos.size; i++) {
			if(eventos.get(i).nombre.equals(nombreEvento)) {
				evento = eventos.get(i);
			}
		}
		return evento;
	}
}
