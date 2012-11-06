package com.lumpundform.lumpundform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase para todos los personajes del juego
 * 
 * @author Sergio
 * 
 */
public abstract class Personaje extends ObjetoActor {
	protected Map<String, Habilidad> habilidades;
	protected float vida;

	protected Personaje(String nombre, Vector2 puntoOrigen) {
		super(nombre);

		habilidades = new HashMap<String, Habilidad>();

		this.x = puntoOrigen.x;
		this.y = puntoOrigen.y;
	}
	
	@Override
	protected void moverDerecha(float delta) {
		super.moverDerecha(delta);
		if (estado != CAYENDO)
			estado = MOVIMIENTO;
	}
	
	@Override
	protected void moverIzquierda(float delta) {
		super.moverIzquierda(delta);
		if (estado != CAYENDO)
			estado = MOVIMIENTO;
	}

	@Override
	protected abstract void cargarAnimaciones();

	@Override
	public void act(float delta) {
		super.act(delta);
		estado = colisionPiso ? DETENIDO : CAYENDO;
		reducirCooldownHabilidades(delta);
	}

	private void reducirCooldownHabilidades(float delta) {
		Iterator<Habilidad> i = habilidades.values().iterator();
		while (i.hasNext()) {
			i.next().reducirCooldown(delta);
		}
	}

	public void quitarVida(float dano) {
		vida -= dano;
		if (vida <= 0.0f) {
			remove();
		}
	}
}
