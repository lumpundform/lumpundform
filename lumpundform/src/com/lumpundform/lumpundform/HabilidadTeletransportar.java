package com.lumpundform.lumpundform;

import com.badlogic.gdx.math.Vector2;

public class HabilidadTeletransportar extends Habilidad {

	public HabilidadTeletransportar(String nombre) {
		super(nombre, 3.0f);
	}

	@Override
	public void ejecutar(Personaje actor, Vector2 pos) {
		if (actor.colisionPiso && cooldown == 0.0f) {
			Poligono piso = ((EscenarioBase) actor.getStage()).piso;
			Vector2 posicionAnterior = actor.getPosicionCentro();

			actor.teletransportar = true;
			actor.direccionX = posicionAnterior.x >= pos.x ? IZQUIERDA
					: DERECHA;
			actor.setPosicionCentro(pos);

			if (piso.estaColisionando(actor.getHitbox())) {
				Vector2 infIzq = actor.getSensor("inf-izq");
				Vector2 infDer = actor.getSensor("inf-der");
				Vector2 supIzq = actor.getSensor("sup-izq");
				Vector2 supDer = actor.getSensor("sup-der");
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
						yFinalAbajo = yAbajo - supIzq.y - actor.height;
						if (yArriba < yAbajo || yFinalAbajo < actor.height) {
							yFinal = yFinalArriba;
						} else {
							yFinal = yFinalAbajo;
						}
					} else if (yArriba != null) {
						yFinal = yArriba + infIzq.y;
					} else if ((yAbajo - supIzq.y - actor.height) < actor.height) {
						float yFinalIzq = piso.linea("arriba", supIzq).yEnX(
								supIzq);
						float yFinalDer = piso.linea("arriba", supDer).yEnX(
								supDer);
						yFinal = yFinalIzq > yFinalDer ? yFinalIzq : yFinalDer;
					} else {
						yFinal = yAbajo - supIzq.y - height;
					}
					actor.setSensorY("inf-izq", yFinal);
				}
			}

			cooldown = cooldownDefault;
		}
	}

}
