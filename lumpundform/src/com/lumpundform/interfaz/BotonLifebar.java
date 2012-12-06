package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;

public class BotonLifebar extends BotonBarra {
	public BotonLifebar(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("lifebar.png")), 1,
				1, 1, 1), escenario);
		xBase = UI.margen;
		width = UI.anchoBarra;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		Heroe heroe = ((EscenarioBase) stage).getHeroe();
		height = UI.altoBarra * heroe.vida / heroe.vidaMax;
	}
}
