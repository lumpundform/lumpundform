package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.EscenarioSinHeroeException;

class BotonManabar extends BotonBarra {
	BotonManabar(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("texturas/manabar.png")), 1, 1, 1, 1), escenario);
		setxBase(getCamara().viewportWidth - UI.anchoBarra - UI.margen);
		setWidth(UI.anchoBarra);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		try {
			Heroe heroe = ((EscenarioBase) getStage()).getHeroe();
			setHeight(UI.altoBarra * heroe.getMana() / heroe.getManaMax());
		} catch (EscenarioSinHeroeException e) {
		}
	}

}
