package com.lumpundform.habilidades;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Linea;
import com.lumpundform.colision.Poligono;
import com.lumpundform.escenario.EscenarioBase;

public class HabilidadTeletransportar extends Habilidad {

	public HabilidadTeletransportar(Personaje actor, String nombre) {
		super(actor, nombre, 3.0f);
	}

	@Override
	public void ejecutar(Vector2 pos) {
		if (pos == null) return;
		super.ejecutar(pos);
		if (!getActor().isTeletransportar()) {
			Poligono piso = ((EscenarioBase) getActor().getStage()).getPiso();
			Vector2 posicionAnterior = getActor().getPosicionCentro();

			getActor().setTeletransportar(true);
			getActor().setDireccionX(posicionAnterior.x >= pos.x ? ObjetoActor.Direccion.IZQUIERDA
					: ObjetoActor.Direccion.DERECHA);
			getActor().setPosicionCentro(pos);

			if (piso.estaColisionando(getActor().getHitbox())) {
				Vector2 infIzq = getActor().getEsquina("inf-izq");
				Vector2 infDer = getActor().getEsquina("inf-der");
				Vector2 supIzq = getActor().getEsquina("sup-izq");
				Vector2 supDer = getActor().getEsquina("sup-der");
				Linea lii = piso.estaColisionando(infIzq) ? piso.linea(
						"arriba", infIzq) : null;
				Linea lid = piso.estaColisionando(infDer) ? piso.linea(
						"arriba", infDer) : null;
				Linea lsi = piso.estaColisionando(supIzq) ? piso.linea("abajo",
						supIzq) : null;
				Linea lsd = piso.estaColisionando(supDer) ? piso.linea("abajo",
						supDer) : null;
				Float yii = null, yid = null, ysi = null, ysd = null;
				Float yArribaIzq = null, yArribaDer = null, yAbajoIzq = null;
				Float yAbajoDer = null, yArriba = null, yAbajo = null;

				if (lii != null || lid != null) {
					if (lii != null) {
						yii = lii.yEnX(infIzq);
					}
					if (lid != null) {
						yid = lid.yEnX(infDer);
					}
					if (yii != null) {
						yArribaIzq = yii - infIzq.y;
					}
					if (yid != null) {
						yArribaDer = yid - infDer.y;
					}

					if (yArribaIzq == null || yArribaDer == null) {
						yArriba = yArribaIzq != null ? yArribaIzq : yArribaDer;
					} else {
						yArriba = yArribaIzq > yArribaDer ? yArribaIzq
								: yArribaDer;
					}
				}
				if (lsi != null || lsd != null) {
					if (lsi != null) {
						ysi = lsi.yEnX(supIzq);
					}
					if (lsd != null) {
						ysd = lsd.yEnX(supDer);
					}
					if (ysi != null) {
						yAbajoIzq = supIzq.y - ysi;
					}
					if (ysd != null) {
						yAbajoDer = supDer.y - ysd;
					}

					if (yAbajoIzq == null || yAbajoDer == null) {
						yAbajo = yAbajoIzq != null ? yAbajoIzq : yAbajoDer;
					} else {
						yAbajo = yAbajoIzq < yAbajoDer ? yAbajoIzq : yAbajoDer;
					}
				}

				if (yArriba != null || yAbajo != null) {
					float yFinalArriba, yFinalAbajo, yFinal;
					if (yArriba != null && yAbajo != null) {
						yFinalArriba = yArriba + infIzq.y;
						yFinalAbajo = yAbajo - supIzq.y - getActor().height;
						if (yArriba < yAbajo || yFinalAbajo < getActor().height) {
							yFinal = yFinalArriba;
						} else {
							yFinal = yFinalAbajo;
						}
					} else if (yArriba != null) {
						yFinal = yArriba + infIzq.y;
					} else if ((yAbajo - supIzq.y - getActor().height) < getActor().height) {
						float yFinalIzq = piso.linea("arriba", supIzq).yEnX(
								supIzq);
						float yFinalDer = piso.linea("arriba", supDer).yEnX(
								supDer);
						yFinal = yFinalIzq > yFinalDer ? yFinalIzq : yFinalDer;
					} else {
						yFinal = yAbajo - supIzq.y - getActor().height;
					}
					getActor().setEsquinaY("inf-izq", yFinal);
				}
			}

			setCooldown(getCooldownDefault());
		}
	}

}
