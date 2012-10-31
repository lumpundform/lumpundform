package com.lumpundform.lumpundform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * La base para los escenarios, extiende a {@link Stage} y agrega funciones y
 * valores específicos del juego
 * 
 * @author Sergio
 * 
 */
public class EscenarioBase extends Stage {
	public Poligono piso;

	public EscenarioBase(float width, float height, boolean stretch,
			SpriteBatch batch) {
		super(width, height, stretch, batch);
	}

	/**
	 * Cambia el estado del {@link Heroe} si este está colisionando con algún
	 * otro {@link ObjetoActor} del escenario
	 */
	public void colisionActores() {
		Heroe heroe = (Heroe) findActor("heroe");
		List<Actor> actores = getActors();

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

	/**
	 * Detecta la colisión de todos los {@link ObjetoActor}es con el piso
	 */
	public void colisionPiso(float delta) {
		List<Actor> actores = getActors();
		Map<String, Boolean> caidaLibre = new HashMap<String, Boolean>();

		for (int i = 0; i < actores.size(); i++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			caidaLibre.put(actor.name, false);

			// Datos para cuando va hacia derecha o izquierda
			String puntoColision, puntoColisionTemp;
			String direccionDiagonalDer, direccionDiagonalIzq, direccionLinea;
			if (actor.direccionX == ObjetoActor.DERECHA) {
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
			boolean lineaNull = true, ln = false;
			Float altura = null;
			String direccionDiagonal = null;
			if (!piso.estaColisionando(actor.getSensor("inf-izq"))
					&& !piso.estaColisionando(actor.getSensor("inf-der"))) {
				caidaLibre.put(actor.name, true);

				Vector2 puntoTemp = actor.getSensor(puntoColisionTemp);
				p = new Vector2(puntoTemp.x, puntoTemp.y - 10);
				ln = true;
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
				if (ln) {
					lineaNull = l != null;
				}

				// Saca la pendiente de la línea
				float pendiente = 0.0f;
				try {
					pendiente = l.pendiente();
				} catch (Exception e) {
					U.err(e);
				}

				// Posiciona al actor sobre la línea si la linea tiene una
				// pendiente
				// menor a 1
				if (lineaNull
						&& pendiente <= 1.01f
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
}
