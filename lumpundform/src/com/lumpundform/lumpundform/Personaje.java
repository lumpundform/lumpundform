package com.lumpundform.lumpundform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

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
	protected Estado estado;

	// Habilidades
	protected Map<String, Habilidad> habilidades;

	// Vida y Mana
	protected float vida;
	protected float mana;
	protected float vidaMax;
	protected float manaMax;
	protected float manaPorSegundo;

	protected Personaje(String nombre, Vector2 puntoOrigen) {
		super(nombre);

		habilidades = new HashMap<String, Habilidad>();

		this.x = puntoOrigen.x;
		this.y = puntoOrigen.y;
	}

	@Override
	protected void moverDerecha(float delta) {
		super.moverDerecha(delta);
		if (estado != Estado.CAYENDO)
			estado = Estado.MOVIMIENTO;
	}

	@Override
	protected void moverIzquierda(float delta) {
		super.moverIzquierda(delta);
		if (estado != Estado.CAYENDO)
			estado = Estado.MOVIMIENTO;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		estado = colisionPiso ? Estado.DETENIDO : Estado.CAYENDO;
		reducirCooldownHabilidades(delta);
		aumentarMana(delta);
	}

	@Override
	protected TextureRegion getCuadroActual() {
		// Revisar de cual animación se va a agarrar el cuadro actual
		String nombreAnimacion;
		switch (estado) {
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
		if (colisionActores) {
			nombreAnimacion = "colisionando";
		}

		U.l("Animacion", nombreAnimacion);
		if (!animacion.containsKey(nombreAnimacion)) {
			nombreAnimacion = "detenido";
		}

		return animacion.get(nombreAnimacion).getKeyFrame(tiempoTranscurrido,
				true);
	}

	private void reducirCooldownHabilidades(float delta) {
		Iterator<Habilidad> i = habilidades.values().iterator();
		while (i.hasNext()) {
			i.next().reducirCooldown(delta);
		}
	}
	
	private void aumentarMana(float delta) {
		if (manaPorSegundo > 0) {
			mana += (manaPorSegundo * delta);
		}
		
		if (mana >= manaMax) {
			mana = manaMax;
		}
	}

	public void quitarVida(float dano) {
		Evento evento = ((EscenarioBase) getStage())
				.getEvento(perteneceAEvento);
		vida -= dano;
		if (vida <= 0.0f) {
			if (evento != null) {
				evento.matarPersonaje();
			}
			remove();
		}
	}
	
	public void quitarMana(float mana) {
		this.mana -= mana;
		if (this.mana <= 0.0f) {
			this.mana = 0.0f;
		}
	}
}
