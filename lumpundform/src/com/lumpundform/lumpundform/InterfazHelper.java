package com.lumpundform.lumpundform;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;

public class InterfazHelper {
	private EscenarioBase escenario;

	public InterfazHelper(EscenarioBase escenario) {
		this.escenario = escenario;
	}

	public void agregarElementos() {
		habilidades();
		botonMenu();
		lifebar();
		manabar();
	}

	private void botonMenu() {
		new BotonMenu(escenario);
	}

	private void habilidades() {
		List<BotonHabilidad> habilidades = new ArrayList<BotonHabilidad>();
		for (int i = 0; i < (UI.cantHabilidades / 2); i++) {
			BotonHabilidad bh1 = new BotonHabilidad(escenario, i);
			BotonHabilidad bh2 = new BotonHabilidad(escenario, i, true);
			habilidades.add(bh1);
			habilidades.add(bh2);
		}

		for (BotonHabilidad habilidad : habilidades) {
			habilidad.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
					U.l("actor", actor);
				}
			});
		}
	}

	private void lifebar() {
		new BotonLifebar(escenario);
	}

	private void manabar() {
		new BotonManabar(escenario);
	}

}
