package com.lumpundform.lumpundform;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * La base para los escenarios, extiende a {@link Stage} y agrega funciones y
 * valores específicos del juego
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
	 * Cambia el estado del héroe si este está colisionando con algún otro
	 * actor del escenario
	 */
	public void colisionActores() {
		Heroe heroe = (Heroe) findActor("heroe");
		List<Actor> actores = getActors();
		
		for (int i = 0; i < actores.size(); i ++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			if (actor.name != "heroe" && heroe.getHitbox().estaColisionando(actor.getHitbox())) {
				heroe.estado = ObjetoActor.COLISIONANDO;
				break;
			} else if (actor.name != "heroe") {
				heroe.estado = ObjetoActor.DETENIDO;
			}
		}
	}
	
	/**
	 * Detecta la colisión de todos los actores con el piso
	 */
	public void colisionPiso() {
		List<Actor> actores = getActors();
		
		for (int i = 0; i < actores.size(); i ++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			if(!piso.estaColisionando(actor.getHitbox())) {
				actor.y -= 3;
			}
		}
	}

}
