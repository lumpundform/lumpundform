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
			if (!piso.estaColisionando(actor.getSensor("inf-izq"))
					&& !piso.estaColisionando(actor.getSensor("inf-der"))) {
				caidaLibre.put(actor.name, true);
			}

			if (actor.direccionX == ObjetoActor.DERECHA) {
				if (!piso.estaColisionando(actor.getSensor("inf-izq"))
						&& !piso.estaColisionando(actor.getSensor("inf-der"))) {
					Vector2 puntoTemp = actor.getSensor("inf-izq");
					Vector2 p = new Vector2(puntoTemp.x, puntoTemp.y - 10);
					Linea l = piso.linea("arriba", p);

					float pendiente = 0.0f;
					try {
						pendiente = l.pendiente();
					} catch (Exception e) {
					}

					if (l != null
							&& pendiente <= 1.01f
							&& l.yEnX(p) <= actor.y
							&& (l.direccionDiagonal() == "abajo" || l
									.direccionLinea() == "x")) {
						caidaLibre.put(actor.name, false);
						actor.y = l.yEnX(p);
					}
				} else if (piso.estaColisionando(actor.getSensor("inf-der"))) {
					Vector2 p = actor.getSensor("inf-der");
					Linea l = piso.linea("arriba", p);

					float pendiente = 0.0f;
					try {
						pendiente = l.pendiente();
					} catch (Exception e) {
						U.err(e);
					}

					if (pendiente <= 1.01f
							&& l.yEnX(p) <= actor.y + 10
							&& (l.direccionDiagonal() == "arriba" || l
									.direccionLinea() == "x")) {
						caidaLibre.put(actor.name, false);
						actor.y = l.yEnX(p);
					}
				}
			} else {
				if (!piso.estaColisionando(actor.getSensor("inf-izq"))
						&& !piso.estaColisionando(actor.getSensor("inf-der"))) {
					Vector2 puntoTemp = actor.getSensor("inf-der");
					Vector2 p = new Vector2(puntoTemp.x, puntoTemp.y - 10);
					Linea l = piso.linea("arriba", p);

					float pendiente = 0.0f;
					try {
						pendiente = l.pendiente();
					} catch (Exception e) {
						U.err(e);
					}

					if (l != null
							&& pendiente <= 1.01f
							&& l.yEnX(p) <= actor.y
							&& (l.direccionDiagonal() == "arriba" || l
									.direccionLinea() == "-x")) {
						caidaLibre.put(actor.name, false);
						actor.y = l.yEnX(p);
					}
				} else if (piso.estaColisionando(actor.getSensor("inf-izq"))) {
					Vector2 p = actor.getSensor("inf-izq");
					Linea l = piso.linea("arriba", p);

					float pendiente = 0.0f;
					try {
						pendiente = l.pendiente();
					} catch (Exception e) {
						U.err(e);
					}

					if (pendiente <= 1.01f
							&& l.yEnX(p) <= actor.y + 10
							&& (l.direccionDiagonal() == "abajo" || l
									.direccionLinea() == "-x")) {
						caidaLibre.put(actor.name, false);
						actor.y = l.yEnX(p);
					}
				}
			}

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
