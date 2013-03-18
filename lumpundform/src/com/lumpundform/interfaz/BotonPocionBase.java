package com.lumpundform.interfaz;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.EscenarioSinHeroeException;

public class BotonPocionBase extends BotonBase {
	private String tipo;
	private int cantidad;

	protected BotonPocionBase(NinePatch ninePatch, EscenarioBase escenario) {
		super(ninePatch, escenario);
		setyBase(getCamara().viewportHeight - UI.margen * 2 - UI.margenMediano - UI.altoBoton - UI.altoBarra
				- UI.altoPocion);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		try {
			setCantidad(((EscenarioBase) getStage()).getHeroe().getPociones().get(getTipo()));
		} catch (EscenarioSinHeroeException e) {
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		bmf.setScale(0.5f);
		float xCant = getX() + UI.anchoPocion + UI.margenChico;
		float yCant = getY() + bmf.getCapHeight();
		bmf.draw(batch, getCantidad() + "", xCant, yCant);
		fade();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
