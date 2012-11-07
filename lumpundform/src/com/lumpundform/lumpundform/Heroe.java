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

		estado = Estado.DETENIDO;
		destinoX = x;
		direccionX = Direccion.DERECHA;
		velocidad = 500;

		vida = 100.0f;
		vidaMaxima = 100.0f;

		cargarAnimaciones("detenido", "corriendo", "colisionando", "cayendo");
		cargarHabilidades();
	}

	private void cargarHabilidades() {
		habilidades.put("teletransportar", new HabilidadTeletransportar(
				"teletransportar"));
		habilidades.put("disparar", new HabilidadDisparar("disparar"));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		moverHeroe(delta);

		vida -= delta;
		if (vida < 0)
			vida = 0;
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
				direccionX = Direccion.IZQUIERDA;
				moverIzquierda(d);
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				direccionX = Direccion.DERECHA;
				moverDerecha(d);
			}
		}
	}

	public void habilidad(String nombre) throws HabilidadInexistenteException {
		habilidad(nombre, null);
	}

	public void habilidad(String nombre, Vector2 pos)
			throws HabilidadInexistenteException {
		if (habilidades.containsKey(nombre)) {
			Habilidad hab = habilidades.get(nombre);
			hab.ejecutar(this, pos);
		} else {
			throw new HabilidadInexistenteException("No existe la habilidad "
					+ nombre + " para el actor " + name);
		}
	}
}
