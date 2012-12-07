package com.lumpundform.actores;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.excepciones.HabilidadInexistenteException;
import com.lumpundform.habilidades.Habilidad;
import com.lumpundform.habilidades.HabilidadDisparar;
import com.lumpundform.habilidades.HabilidadTeletransportar;
import com.lumpundform.utilerias.U;

/**
 * Clase específica para el héroe del juego
 * 
 * @author Sergio
 * 
 */
public class Heroe extends Personaje {
	private List<Habilidad> habilidadesInterfaz;

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

		setWidth(125.0f);
		setHeight(150.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth() / 3));

		setEstado(Estado.DETENIDO);
		setDestinoX(getX());
		setDireccionX(Direccion.DERECHA);
		setVelocidad(500);

		setVida(100.0f);
		setVidaMax(100.0f);
		setMana(100.0f);
		setManaMax(100.0f);
		setManaPorSegundo(5.0f);

		cargarAnimaciones("detenido", "corriendo", "colisionando", "cayendo");
		cargarHabilidades();
	}

	private void cargarHabilidades() {
		getHabilidades().put("teletransportar",
				new HabilidadTeletransportar(this, "teletransportar"));
		getHabilidades().put("disparar",
				new HabilidadDisparar(this, "disparar"));

		// TODO: cargar habilidadesInterfaz de los settings
		setHabilidadesInterfaz(new ArrayList<Habilidad>());
		try {
			getHabilidadesInterfaz().add(getHabilidad("disparar"));
			getHabilidadesInterfaz().add(getHabilidad("teletransportar"));
		} catch (HabilidadInexistenteException e) {
			U.err(e);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (isColisionActores()) {
			hacerDano();
		}

		moverHeroe(delta);
	}

	private void hacerDano() {
		// TODO: Implementar un tiempo de invincibilidad después de recibir daño
		setVida(getVida() - 1);

		if (getVida() < 0) {
			setVida(0);
		}
	}

	/**
	 * Mueve al {@link Heroe} al presionar las teclas adecuadas
	 * 
	 * @param delta
	 *            El delta de {@link Screen#render()}
	 */
	private void moverHeroe(float delta) {
		if (!isTeletransportar()
				&& (Gdx.input.isKeyPressed(Keys.A) || Gdx.input
						.isKeyPressed(Keys.D))) {
			float d = delta;
			if (!isColisionPiso()) {
				d = delta * 0.75f;
			}
			if (Gdx.input.isKeyPressed(Keys.A)) {
				setDireccionX(Direccion.IZQUIERDA);
				moverIzquierda(d);
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				setDireccionX(Direccion.DERECHA);
				moverDerecha(d);
			}
		}
	}

	public void habilidad(String nombre) throws HabilidadInexistenteException {
		habilidad(nombre, null);
	}

	public void habilidad(String nombre, Vector2 pos)
			throws HabilidadInexistenteException {
		if (getHabilidades().containsKey(nombre)) {
			getHabilidades().get(nombre).ejecutar(pos);
		} else {
			throw new HabilidadInexistenteException("No existe la habilidad "
					+ nombre + " para el actor " + getName());
		}
	}

	private Habilidad getHabilidad(String nombre)
			throws HabilidadInexistenteException {
		if (getHabilidades().containsKey(nombre)) {
			return getHabilidades().get(nombre);
		} else {
			throw new HabilidadInexistenteException("No existe la habilidad "
					+ nombre + " para el actor " + getName());
		}
	}

	public List<Habilidad> getHabilidadesInterfaz() {
		return habilidadesInterfaz;
	}

	public void setHabilidadesInterfaz(List<Habilidad> habilidadesInterfaz) {
		this.habilidadesInterfaz = habilidadesInterfaz;
	}
}
