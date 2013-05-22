package com.lumpundform.acciones;

import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.Personaje;
import com.lumpundform.actores.Personaje.Estado;
import com.lumpundform.habilidades.Habilidad;
import com.lumpundform.habilidades.HabilidadBlizzard;
import com.lumpundform.indicadores.BarraVida;
import com.lumpundform.utilerias.U;

/**
 * Las acciones que realizan los {@link Personaje}s al llamar su función
 * {@link Personaje#act(float)}.
 * 
 * @author Sergio Valencia
 * 
 */
public class PersonajeAction extends Action {

	@Override
	public boolean act(float delta) {
		Personaje p = (Personaje) getActor();

		if (p != null) {
			if (p.getBarraVida() == null) {
				p.setBarraVida(new BarraVida(p));
			}
			p.setEstado(p.isColisionPiso() ? Estado.DETENIDO : Estado.CAYENDO);
			p.reducirCooldownHabilidades(delta);
			p.aumentarMana(delta);

			if (p.isEnemigo()) {
				p.voltearHaciaHeroe();
				if (p.lejosDeHeroe() && !p.colisionConEnemigos()) {
					p.moverEnDireccion();
				}
			}

			for (Map.Entry<String, Habilidad> habilidad : p.getHabilidades()
					.entrySet()) {
				Habilidad hab = habilidad.getValue();
				if ((hab instanceof HabilidadBlizzard) && hab.isEjecutandose()) {
					((HabilidadBlizzard) hab).aumentarTiempoTranscurrido(delta);
				}
			}
		}

		return false;
	}

}