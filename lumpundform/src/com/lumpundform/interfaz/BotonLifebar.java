package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.EscenarioSinHeroeException;

class BotonLifebar extends BotonBarra {
	BotonLifebar(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("texturas/lifebar.png")), 1, 1, 1, 1), escenario);
		setxBase(UI.margen);
		setWidth(UI.anchoBarra);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		try {
			Heroe heroe = ((EscenarioBase) getStage()).getHeroe();
			setHeight(UI.altoBarra * heroe.getVida() / heroe.getVidaMax());
		} catch (EscenarioSinHeroeException e) {
			setHeight(0);
		}
	}
}
