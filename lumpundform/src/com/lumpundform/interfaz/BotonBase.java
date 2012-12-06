package com.lumpundform.interfaz;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.lumpundform.CamaraJuego;

public class BotonBase extends Button {
	protected CamaraJuego camara;
	protected float xBase;
	protected float yBase;

	public BotonBase(NinePatch patch, EscenarioBase escenario) {
		super(patch);
		escenario.addActor(this);
		camara = (CamaraJuego) escenario.getCamera();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		x = xBase + camara.getPosicionOrigen().x;
		y = yBase + camara.getPosicionOrigen().y;
	}

}
