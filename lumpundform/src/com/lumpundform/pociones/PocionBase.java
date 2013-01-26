package com.lumpundform.pociones;

import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.colision.Rectangulo;

public class PocionBase extends ObjetoActor {
	public class Estado {
		public static final String NORMAL = "normal";
		public static final String DEFAULT = "normal";
	}

	private String tipo;

	public PocionBase(Vector2 posicion, String tipo) {
		super("pocion_" + tipo);

		setTipo(tipo);

		setEstado(Estado.NORMAL);
		setEstadoDefault(Estado.DEFAULT);

		setX(posicion.x);
		setY(posicion.y);
		setWidth(15.0f);
		setHeight(20.0f);

		setHitbox(new Rectangulo(getHeight(), getWidth()));

		setCaer(true);

		cargarAnimaciones("normal");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}