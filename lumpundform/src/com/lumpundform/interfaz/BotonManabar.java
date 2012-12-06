package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;

public class BotonManabar extends BotonBarra {
	public BotonManabar(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("manabar.png")), 1,
				1, 1, 1), escenario);
		xBase = camara.viewportWidth - UI.anchoBarra - UI.margen;
		width = UI.anchoBarra;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		Heroe heroe = ((EscenarioBase) stage).getHeroe();
		height = UI.altoBarra * heroe.mana / heroe.manaMax;
	}

}
