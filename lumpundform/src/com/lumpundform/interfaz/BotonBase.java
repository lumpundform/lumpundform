package com.lumpundform.interfaz;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.lumpundform.CamaraJuego;
import com.lumpundform.utilerias.Fuentes;

class BotonBase extends Button {
	private CamaraJuego camara;
	private float xBase;
	private float yBase;
	BitmapFont bmf = Fuentes.regular();

	protected BotonBase(NinePatch patch, EscenarioBase escenario) {
		super(new NinePatchDrawable(patch));
		escenario.addActor(this);
		camara = (CamaraJuego) escenario.getCamera();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		setZIndex(100);
		setX(getxBase() + camara.getPosicionOrigen().x);
		setY(getyBase() + camara.getPosicionOrigen().y);
	}

	public CamaraJuego getCamara() {
		return camara;
	}

	public void setCamara(CamaraJuego camara) {
		this.camara = camara;
	}

	public float getyBase() {
		return yBase;
	}

	public void setyBase(float yBase) {
		this.yBase = yBase;
	}

	public float getxBase() {
		return xBase;
	}

	public void setxBase(float xBase) {
		this.xBase = xBase;
	}

}
