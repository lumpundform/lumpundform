package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.HabilidadInexistenteException;
import com.lumpundform.habilidades.Habilidad;
import com.lumpundform.utilerias.U;

public class BotonHabilidad extends BotonSuperior {
	private Habilidad habilidad;

	public BotonHabilidad(EscenarioBase escenario, Habilidad habilidad,
			int posicion) {
		super(new NinePatch(new Texture(Gdx.files.internal("manabar.png")), 1,
				1, 1, 1), escenario);

		this.habilidad = habilidad;

		this.width = anchoBoton();
		this.height = UI.altoBoton;
		this.xBase = xHabilidad(posicion);
		this.yBase = yBoton();

		setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				BotonHabilidad boton = (BotonHabilidad) actor;
				Heroe heroe = ((EscenarioBase) boton.getStage()).getHeroe();
				try {
					heroe.habilidad(boton.habilidad.nombre);
				} catch (HabilidadInexistenteException e) {
					U.err(e);
				}
			}
		});
	}

	private float xHabilidad(int posicion) {
		if (posicion > (UI.cantHabilidades / 2)) {
			return UI.margen
					+ ((camara.viewportWidth - (2 * UI.margen)))
					- anchoBoton()
					- ((UI.cantHabilidades - posicion) * (anchoBoton() + UI.margen));
		} else {
			return UI.margen + ((posicion - 1) * (anchoBoton() + UI.margen));
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		float alpha = 0;
		if (habilidad.sePuedeEjecutar()) {
			alpha = parentAlpha;
		} else {
			alpha = parentAlpha * 0.5f;
		}
		super.draw(batch, alpha);
	}

}
