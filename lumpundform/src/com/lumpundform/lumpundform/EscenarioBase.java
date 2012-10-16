package com.lumpundform.lumpundform;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class EscenarioBase extends Stage {
	public Poligono piso;

	public EscenarioBase(float width, float height, boolean stretch,
			SpriteBatch batch) {
		super(width, height, stretch, batch);
	}

	public void colisionActores() {
		Heroe heroe = (Heroe) findActor("heroe");
		List<Actor> actores = getActors();
		
		for (int i = 0; i < actores.size(); i ++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			if (actor.name != "heroe" && heroe.getHitbox().estaColisionando(actor.getHitbox())) {
				heroe.estado = ObjetoActor.COLISIONANDO;
			} else if (actor.name != "heroe") {
				heroe.estado = ObjetoActor.DETENIDO;
			}
		}
	}

}
