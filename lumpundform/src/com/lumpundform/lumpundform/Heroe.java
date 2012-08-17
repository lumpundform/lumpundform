package com.lumpundform.lumpundform;

public class Heroe extends Personaje {

	/**
	 * Inicializa la posición, el tamaño, el movimiento, las textura y las
	 * animaciones del héroe.
	 */
	public Heroe(String nombre) {
		super(nombre);

		width = 125;
		height = 150;
		x = 20 + (width / 2);
		y = 20 + (height / 2);

		estado = DE_PIE;
		destinoX = x;
		direccionX = DERECHA;
		velocidad = 500;

		/****** Animaciones ******/
		// De pie
		nombreImagen.put("dePie", "samus_sprite_sheet_crop.png");
		columnas.put("dePie", 1);
		renglones.put("dePie", 1);
		animacion.put("dePie", initAnimacion("dePie"));
		// Corriendo
		nombreImagen.put("corriendo", "samus_corriendo.png");
		columnas.put("corriendo", 11);
		renglones.put("corriendo", 1);
		animacion.put("corriendo", initAnimacion("corriendo"));
		// Saltando
		nombreImagen.put("cayendo", "samus_cayendo.png");
		columnas.put("cayendo", 1);
		renglones.put("cayendo", 1);
		animacion.put("cayendo", initAnimacion("cayendo"));
	}
}
