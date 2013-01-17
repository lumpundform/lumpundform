package com.lumpundform.actores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.acciones.PersonajeAction;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.eventos.Evento;
import com.lumpundform.habilidades.Habilidad;
import com.lumpundform.interfaz.EtiquetaCantidad;

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

		setCaer(true);

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
			setDireccionX(Direccion.IZQUIERDA);
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

		return getAnimacion().get(nombreAnimacion).getKeyFrame(getTiempoTranscurrido(), true);
	}

	public Direccion getDireccionPosicionHeroe() {
		Heroe heroe = getHeroeEscenario();
		if (heroe == null) {
			return null;
		} else if (heroe.getPosicionCentro().x < getPosicionCentro().x) {
			return Direccion.IZQUIERDA;
		} else {
			return Direccion.DERECHA;
		}
	}

	public Heroe getHeroeEscenario() {
		EscenarioBase escenario = (EscenarioBase) getStage();
		return escenario.getHeroe();
	}

	public void reducirCooldownHabilidades(float delta) {
		Iterator<Habilidad> i = getHabilidades().values().iterator();
		while (i.hasNext()) {
			i.next().reducirCooldown(delta);
		}
	}

	public void aumentarMana(float delta) {
		if (getManaPorSegundo() > 0) {
			setMana(getMana() + (getManaPorSegundo() * delta));
		}

		if (getMana() >= getManaMax()) {
			setMana(getManaMax());
		}
	}

	public void quitarVida(float dano) {
		EscenarioBase escenario = (EscenarioBase) getStage();
		Evento evento = escenario.getEvento(getPerteneceAEvento());
		hacerDano(dano);
		if (getVida() <= 0.0f) {
			if (evento != null) {
				evento.matarPersonaje();
			}
			if (isEnemigo()) {
				escenario.crearPocion(getPosicionCentro());
				remove();
			} else {
				// TODO: Que hacer cuando el héroe muere
			}
		}
	}

	protected void hacerDano(float dano) {
		getStage().addActor(new EtiquetaCantidad(dano + "", getEsquina("sup-izq")));
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
