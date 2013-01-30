package com.lumpundform.ataques;

import com.lumpundform.actores.Personaje;
import com.lumpundform.colision.Rectangulo;

public class AtaqueEscudo extends Ataque {

	public AtaqueEscudo(Personaje personaje) {
		super("ataque_escudo", personaje);
		
		setWidth(120.0f);
		setHeight(71.0f);
		
		setHitbox(new Rectangulo(getHeight(), getWidth()));
		
		setY(personaje.getY());
		setX(personaje.getX());
		
		cargarAnimaciones("normal");
	}

}
