package com.lumpundform.interfaz;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lumpundform.actores.Heroe;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.BotonHabilidadInexistenteException;
import com.lumpundform.habilidades.Habilidad;
import com.lumpundform.utilerias.U;

public class InterfazHelper {
	private EscenarioBase escenario;

	public InterfazHelper(EscenarioBase escenario) {
		this.escenario = escenario;
	}

	public void agregarElementos() {
		botonMenu();
		lifebar();
		manabar();
		pociones();
		agregarHabilidades();
	}

	private void agregarHabilidades() {
		Heroe heroe = escenario.getHeroe();
		for (int i = 0; i < heroe.getHabilidadesInterfaz().size(); i++) {
			Habilidad habilidad = heroe.getHabilidadesInterfaz().get(i);
			new BotonHabilidad(escenario, habilidad, i + 1);
		}
	}

	public void ejecutarHabilidad(int posicion) {
		try {
			escenario.getHeroe().habilidad(getBotonHabilidad(posicion).getHabilidad().getNombre());
		} catch (BotonHabilidadInexistenteException e) {
			U.err(e);
		}
	}

	private BotonHabilidad getBotonHabilidad(int posicion) throws BotonHabilidadInexistenteException {
		List<Actor> habilidades = escenario.getActores(BotonHabilidad.class);
		for (Actor a : habilidades) {
			BotonHabilidad bh = (BotonHabilidad) a;
			if (bh.getPosicion() == posicion) {
				return bh;
			}
		}
		throw new BotonHabilidadInexistenteException("No hay una habilidad en la posición " + posicion);
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

	private void pociones() {
		new BotonPocionVida(escenario);
		new BotonPocionMana(escenario);
	}

}
