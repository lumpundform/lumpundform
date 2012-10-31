package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase específica para el héroe del juego
 * 
 * @author Sergio
 * 
 */
public class Heroe extends Personaje {

	/**
	 * Carga datos específicos del {@link Heroe}, incluyendo su hitbox y su
	 * estado inicial
	 * 
	 * @param nombre
	 *            El nombre del {@link @ObjetoActor}
	 * @param puntoOrigen
	 *            El punto donde se va a originar el {@link ObjetoActor}
	 */
	public Heroe(String nombre, Vector2 puntoOrigen) {
		super(nombre, puntoOrigen);

		width = 125.0f;
		height = 150.0f;

		hitbox = new Rectangulo(height, width / 3, true);

		estado = DETENIDO;
		destinoX = x;
		direccionX = DERECHA;
		velocidad = 500;

		cargarAnimaciones();
	}

	@Override
	protected void cargarAnimaciones() {
		try {
			animacion.put("detenido", initAnimacion("detenido"));
			animacion.put("corriendo", initAnimacion("corriendo"));
			animacion.put("colisionando", initAnimacion("colisionando"));
			animacion.put("cayendo", initAnimacion("cayendo"));
		} catch (NullPointerException e) {
			U.err(e);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		moverHeroe(delta);
	}

	/**
	 * Mueve al {@link Heroe} al presionar las teclas adecuadas
	 * 
	 * @param delta
	 *            El delta de {@link Screen#render()}
	 */
	private void moverHeroe(float delta) {
		if (!teletransportar
				&& (Gdx.input.isKeyPressed(Keys.A) || Gdx.input
						.isKeyPressed(Keys.D))) {
			float d = delta;
			if (!colisionPiso) {
				d = delta * 0.75f;
			}
			if (Gdx.input.isKeyPressed(Keys.A)) {
				direccionX = IZQUIERDA;
				moverIzquierda(d);
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				direccionX = DERECHA;
				moverDerecha(d);
			}
		}
	}

	/**
	 * Funcionalidad para teletransportar al {@link Heroe} al hacer doble click
	 * o doble tap
	 * 
	 * @param pos
	 *            La posición en la que se hizo el doble click o doble tap
	 * @param piso
	 *            El {@link Poligono} del piso del escenario donde se encuentra
	 *            el {@link Heroe}
	 */
	public void teletransportar(Vector2 pos, Poligono piso) {
		if (colisionPiso) {
			Vector2 posicionAnterior = getPosicionCentro();

			teletransportar = true;
			direccionX = posicionAnterior.x >= pos.x ? IZQUIERDA : DERECHA;
			setPosicionCentro(pos);

			if (piso.estaColisionando(getHitbox())) {
				Vector2 infIzq = getSensor("inf-izq");
				Vector2 infDer = getSensor("inf-der");
				Vector2 supIzq = getSensor("sup-izq");
				Vector2 supDer = getSensor("sup-der");
				Linea lii = piso.estaColisionando(infIzq) ? piso.linea(
						"arriba", infIzq) : null;
				Linea lid = piso.estaColisionando(infDer) ? piso.linea(
						"arriba", infDer) : null;
				Linea lsi = piso.estaColisionando(supIzq) ? piso.linea("abajo",
						supIzq) : null;
				Linea lsd = piso.estaColisionando(supDer) ? piso.linea("abajo",
						supDer) : null;
				Float yii = null, yid = null, ysi = null, ysd = null;
				Float yArribaIzq = null, yArribaDer = null, yAbajoIzq = null, yAbajoDer = null, yArriba = null, yAbajo = null;

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
						yFinalAbajo = yAbajo - supIzq.y - height;
						if (yArriba < yAbajo || yFinalAbajo < height) {
							yFinal = yFinalArriba;
						} else {
							yFinal = yFinalAbajo;
						}
					} else if (yArriba != null) {
						yFinal = yArriba + infIzq.y;
					} else if (yAbajo - supIzq.y - height < height) {
						float yFinalIzq = piso.linea("arriba", supIzq).yEnX(
								supIzq);
						float yFinalDer = piso.linea("arriba", supDer).yEnX(
								supDer);
						yFinal = yFinalIzq > yFinalDer ? yFinalIzq : yFinalDer;
					} else {
						yFinal = yAbajo - supIzq.y - height;
					}
					setSensorY("inf-izq", yFinal);
				}
			}
		}
	}
}
