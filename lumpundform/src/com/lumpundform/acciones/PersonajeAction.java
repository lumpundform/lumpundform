package com.lumpundform.acciones;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor.Direccion;
import com.lumpundform.actores.Personaje;
import com.lumpundform.actores.Personaje.Estado;
import com.lumpundform.indicadores.BarraVida;

public class PersonajeAction extends Action {

	@Override
	public boolean act(float delta) {
		Personaje p = (Personaje) getActor();

		if (p != null) {
			if(p.getBarraVida() == null) {
				p.setBarraVida(new BarraVida(p));
			}
			p.setEstado(p.isColisionPiso() ? Estado.DETENIDO : Estado.CAYENDO);
			p.reducirCooldownHabilidades(delta);
			p.aumentarMana(delta);

			if (p.isEnemigo()) {
				Heroe heroe = p.getHeroeEscenario();
				Direccion direccion = p.getDireccionPosicionHeroe();
				if (direccion != null) {
					float distanciaAlejamiento = 200.0f;
					p.setDireccionX(direccion);
					if (direccion == Direccion.DERECHA
							&& (heroe.getXCentro() - p.getXCentro()) > distanciaAlejamiento) {
						p.moverDerecha(delta);
					} else if (direccion == Direccion.IZQUIERDA
							&& (p.getXCentro() - heroe.getXCentro()) > distanciaAlejamiento) {
						p.moverIzquierda(delta);
					}
				}
			}
		}

		return false;
	}

}
