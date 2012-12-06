package com.lumpundform.interfaz;

import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.habilidades.Habilidad;

public class InterfazHelper {
	private EscenarioBase escenario;

	public InterfazHelper(EscenarioBase escenario) {
		this.escenario = escenario;
	}

	public void agregarElementos() {
		botonMenu();
		lifebar();
		manabar();
	}

	public void agregarHabilidades(Heroe heroe) {
		for (int i = 0; i < heroe.getHabilidadesInterfaz().size(); i++) {
			Habilidad habilidad = heroe.getHabilidadesInterfaz().get(i);
			new BotonHabilidad(escenario, habilidad, i + 1);
		}
	}

	private void botonMenu() {
		new BotonMenu(escenario);
	}

	private void lifebar() {
		new BotonLifebar(escenario);
	}

	private void manabar() {
		new BotonManabar(escenario);
	}

}
