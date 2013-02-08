package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.EscenarioSinHeroeException;

public class BotonPocionVida extends BotonPocionBase {

	protected BotonPocionVida(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("texturas/pocion_vida.png")), 0, 0, 0, 0), escenario);
		setxBase(UI.margen);
		setTipo("vida");
		try {
			setCantidad(escenario.getHeroe().getPociones().get("vida"));
		} catch (EscenarioSinHeroeException e) {
			setCantidad(0);
		}
	}

}
