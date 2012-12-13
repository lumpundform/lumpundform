package com.lumpundform.actores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.acciones.PersonajeAction;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.eventos.Evento;
import com.lumpundform.excepciones.EscenarioSinHeroeException;
import com.lumpundform.habilidades.Habilidad;
import com.lumpundform.utilerias.U;

/**
 * Clase para todos los personajes del juego
 * 
 * @author Sergio
 * 
 */
public abstract class Personaje extends ObjetoActor {
	// Valores estáticos de los personajes
	public static enum Estado {
		DETENIDO, MOVIMIENTO, CAYENDO, COLISIONANDO;
	}

	// Estado
	private Estado estado;

	// Habilidades
	private Map<String, Habilidad> habilidades;

	// Vida y Mana
	private float vida;
	private float mana;
	private float vidaMax;
	private float manaMax;
	private float manaPorSegundo;

	private boolean enemigo;

	protected Personaje(String nombre, Vector2 puntoOrigen) {
		super(nombre);

		setHabilidades(new HashMap<String, Habilidad>());

		setX(puntoOrigen.x);
		setY(puntoOrigen.y);

		addAction(new PersonajeAction());
	}

	protected abstract void cargarHabilidades();
	
	public void moverDestino(float delta) {
		if (getDireccionDestinoX() == Direccion.IZQUIERDA && getX() > getDestinoX()) {
			moverIzquierda(delta);
		} else if (getDireccionDestinoX() == Direccion.DERECHA && getX() < getDestinoX()) {
			moverDerecha(delta);
		}
	}

	@Override
	public void moverDerecha(float delta) {
		super.moverDerecha(delta);
		if (getEstado() != Estado.CAYENDO) {
			setEstado(Estado.MOVIMIENTO);
			setDireccionX(Direccion.DERECHA);
		}
	}

	@Override
	public void moverIzquierda(float delta) {
		super.moverIzquierda(delta);
		if (getEstado() != Estado.CAYENDO) {
			setEstado(Estado.MOVIMIENTO);
<<<<<<< HEAD
			setDireccionX(Direccion.IZQUIERDA);
=======
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		setEstado(isColisionPiso() ? Estado.DETENIDO : Estado.CAYENDO);
		reducirCooldownHabilidades(delta);
		aumentarMana(delta);

		// Los enemigos se mueven en la dirección en la que se encuentra el
		// héroe
		if (isEnemigo()) {
			Direccion direccion = getDireccionPosicionHeroe();
<<<<<<< HEAD
			setDireccionX(direccion);
			if (direccion == Direccion.DERECHA) {
				moverDerecha(delta);
			} else {
				moverIzquierda(delta);
=======
			if (direccion != null) {
				float distanciaAlejamiento = 200.0f;
				setDireccionX(direccion);
				if (direccion == Direccion.DERECHA
						&& (getHeroeEscenario().getPosicionCentro().x - getPosicionCentro().x) > distanciaAlejamiento) {
					moverDerecha(delta);
				} else if (direccion == Direccion.IZQUIERDA
						&& (getPosicionCentro().x - getHeroeEscenario()
								.getPosicionCentro().x) > distanciaAlejamiento) {
					moverIzquierda(delta);
				}
>>>>>>> Los enemigos caminan en dirección del héroe
			}
>>>>>>> Los enemigos caminan en dirección del héroe
		}
	}

	@Override
	protected TextureRegion getCuadroActual() {
		// Revisar de cual animación se va a agarrar el cuadro actual
		String nombreAnimacion;
		switch (getEstado()) {
		case DETENIDO:
		default:
			nombreAnimacion = "detenido";
			break;
		case MOVIMIENTO:
			nombreAnimacion = "corriendo";
			break;
		case CAYENDO:
			nombreAnimacion = "cayendo";
			break;
		}
		if (isColisionActores()) {
			nombreAnimacion = "colisionando";
		}

		if (!getAnimacion().containsKey(nombreAnimacion)) {
			nombreAnimacion = "detenido";
		}

		return getAnimacion().get(nombreAnimacion).getKeyFrame(
				getTiempoTranscurrido(), true);
	}

<<<<<<< HEAD
	public Direccion getDireccionPosicionHeroe() {
		Heroe heroe = getHeroeEscenario();
		if (heroe == null) {
			return null;
<<<<<<< HEAD
		} else if (heroe.getPosicionCentro().x < getPosicionCentro().x) {
=======
	private Direccion getDireccionPosicionHeroe() {
		Heroe heroe = getHeroeEscenario();
		if (heroe.getPosicionCentro().x < getPosicionCentro().x) {
>>>>>>> Los enemigos caminan en dirección del héroe
=======
		} else if (heroe.getPosicionCentro().x < getPosicionCentro().x) {
>>>>>>> Los enemigos caminan en dirección del héroe
			return Direccion.IZQUIERDA;
		} else {
			return Direccion.DERECHA;
		}
	}

<<<<<<< HEAD
	public Heroe getHeroeEscenario() {
		EscenarioBase escenario = (EscenarioBase) getStage();
<<<<<<< HEAD
		return escenario.getHeroe();
=======
	private Heroe getHeroeEscenario() {
		try {
			EscenarioBase escenario = (EscenarioBase) getStage();
			return escenario.getHeroe();
		} catch (EscenarioSinHeroeException e) {
			U.err(e);
		}
		return null;
>>>>>>> Los enemigos caminan en dirección del héroe
=======
		return escenario.getHeroe();
>>>>>>> Los enemigos caminan en dirección del héroe
	}

	public void reducirCooldownHabilidades(float delta) {
		Iterator<Habilidad> i = getHabilidades().values().iterator();
		while (i.hasNext()) {
			i.next().reducirCooldown(delta);
		}
	}

<<<<<<< HEAD
	public void aumentarMana(float delta) {
=======
	private void aumentarMana(float delta) {
>>>>>>> Los enemigos caminan en dirección del héroe
		if (getManaPorSegundo() > 0) {
			setMana(getMana() + (getManaPorSegundo() * delta));
		}

		if (getMana() >= getManaMax()) {
			setMana(getManaMax());
		}
	}

	public void quitarVida(float dano) {
		Evento evento = ((EscenarioBase) getStage()).getEvento(getPerteneceAEvento());
		hacerDano(dano);
		if (getVida() <= 0.0f) {
			if (evento != null) {
				evento.matarPersonaje();
			}
			if (isEnemigo()) {
				remove();
			} else {
				// TODO: Que hacer cuando el héroe muere
			}
		}
	}

	protected void hacerDano(float dano) {
		setVida(getVida() - dano);

		if (getVida() < 0) {
			setVida(0);
		}
	}

	public void quitarMana(float mana) {
		this.setMana(this.getMana() - mana);
		if (this.getMana() <= 0.0f) {
			this.setMana(0.0f);
		}
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public float getVida() {
		return vida;
	}

	public void setVida(float vida) {
		this.vida = vida;
	}

	public float getVidaMax() {
		return vidaMax;
	}

	public void setVidaMax(float vidaMax) {
		this.vidaMax = vidaMax;
	}

	public float getMana() {
		return mana;
	}

	public void setMana(float mana) {
		this.mana = mana;
	}

	public float getManaMax() {
		return manaMax;
	}

	public void setManaMax(float manaMax) {
		this.manaMax = manaMax;
	}

	public float getManaPorSegundo() {
		return manaPorSegundo;
	}

	public void setManaPorSegundo(float manaPorSegundo) {
		this.manaPorSegundo = manaPorSegundo;
	}

	public Map<String, Habilidad> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(Map<String, Habilidad> habilidades) {
		this.habilidades = habilidades;
	}

	public boolean isEnemigo() {
		return enemigo;
	}

	public void setEnemigo(boolean enemigo) {
		this.enemigo = enemigo;
	}
}
