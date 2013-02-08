package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.EscenarioSinHeroeException;

public class BotonPocionMana extends BotonPocionBase {

	protected BotonPocionMana(EscenarioBase escenario) {
		super(new NinePatch(new Texture(Gdx.files.internal("texturas/pocion_mana.png")), 0, 0, 0, 0), escenario);
		setxBase(getCamara().viewportWidth - UI.anchoBarra - UI.margen);
		setTipo("mana");
		try {
			setCantidad(escenario.getHeroe().getPociones().get("mana"));
		} catch (EscenarioSinHeroeException e) {
			setCantidad(0);
		}
	}

}
