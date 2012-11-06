package com.lumpundform.lumpundform;

import java.util.HashMap;
import java.util.Map;

/**
 * Contiene los datos necesarios para el juego, como los nombres de los archivos
 * de sprite_sheet de los objetos, las columnas y renglones que usan cada una.
 * 
 * Para sacar el sprite sheet, se usa la funcion 's':
 * Datos.s("heroe").get("sprite_sheet") Para sacar las columnas de alguna
 * animacion se usa la funcion 'i': Datos.i("heroe").get("columnas_" +
 * nombreAnimacion) Para sacar los renglones de alguna animacion se usa la
 * funcion 'i': Datos.i("heroe").get("renglones_" + nombreAnimacion)
 * 
 * @author Sergio
 * 
 */
public class D {
	/**
	 * Regresa un {@link Map} de datos del objeto del nombre dado con todos los
	 * datos tipo {@link String} que haya
	 * 
	 * @param nombre
	 *            El nombre del objeto del cual sacar el {@link Map}
	 * @return Un {@link Map} con todos los datos {@link String} del objeto
	 */
	@SuppressWarnings("unchecked")
	static public Map<String, String> s(String nombre) {
		return datosString().get(nombre);
	}

	/**
	 * Regresa un {@link Map} de datos del objeto del nombre dado con todos los
	 * datos tipo {@link Integer} que haya
	 * 
	 * @param nombre
	 *            El nombre del objeto del cual sacar el {@link Map}
	 * @return Un {@link Map} con todos los datos tipo {@link Integer} del
	 *         objeto
	 */
	@SuppressWarnings("unchecked")
	static public Map<String, Integer> i(String nombre) {
		return datosInteger().get(nombre);
	}

	@SuppressWarnings("rawtypes")
	static private Map<String, Map> datosString() {
		// Datos del Heroe
		Map<String, String> datosHeroe = new HashMap<String, String>();
		datosHeroe.put("sprite_sheet_detenido", "heroe_sprite_sheet.png");
		datosHeroe.put("sprite_sheet_corriendo", "heroe_corriendo.png");
		datosHeroe.put("sprite_sheet_colisionando", "heroe_colisionando.png");
		datosHeroe.put("sprite_sheet_cayendo", "heroe_cayendo.png");

		// Datos del Amigo
		Map<String, String> datosAmigo = new HashMap<String, String>();
		datosAmigo.put("sprite_sheet_detenido", "amigo_sprite_sheet.png");
		datosAmigo.put("sprite_sheet_corriendo", "amigo_corriendo.png");

		// Datos del escenario 1-01
		Map<String, String> escenario101 = new HashMap<String, String>();
		escenario101.put("archivo_tmx", "data/world/level1/level.tmx");
		escenario101.put("atlas", "data/packer/bosque/");
		escenario101.put("fondo", "data/background.png");
		
		// Datos del Ataque Misil
		Map<String, String> datosAtaqueMisil = new HashMap<String, String>();
		datosAtaqueMisil.put("sprite_sheet_normal", "ataque_misil.png");
		datosAtaqueMisil.put("sprite_sheet_explosion", "ataque_misil.png");

		Map<String, Map> datos = new HashMap<String, Map>();
		datos.put("heroe", datosHeroe);
		datos.put("amigo", datosAmigo);
		datos.put("escenario101", escenario101);
		datos.put("ataque_misil", datosAtaqueMisil);

		return datos;
	}

	@SuppressWarnings("rawtypes")
	static private Map<String, Map> datosInteger() {
		// Datos del heroe
		Map<String, Integer> datosHeroe = new HashMap<String, Integer>();
		datosHeroe.put("columnas_detenido", 1);
		datosHeroe.put("renglones_detenido", 1);
		datosHeroe.put("columnas_corriendo", 10);
		datosHeroe.put("renglones_corriendo", 1);
		datosHeroe.put("columnas_colisionando", 8);
		datosHeroe.put("renglones_colisionando", 1);
		datosHeroe.put("columnas_cayendo", 1);
		datosHeroe.put("renglones_cayendo", 1);

		// Datos del amigo
		Map<String, Integer> datosAmigo = new HashMap<String, Integer>();
		datosAmigo.put("columnas_detenido", 1);
		datosAmigo.put("renglones_detenido", 1);
		datosAmigo.put("columnas_corriendo", 10);
		datosAmigo.put("renglones_corriendo", 1);
		
		// Datos del ataque misil
		Map<String, Integer> datosAtaqueMisil = new HashMap<String, Integer>();
		datosAtaqueMisil.put("columnas_normal", 3);
		datosAtaqueMisil.put("renglones_normal", 1);
		datosAtaqueMisil.put("columnas_explosion", 10);
		datosAtaqueMisil.put("renglones_explosion", 1);
		datosAtaqueMisil.put("columnas_offset_explosion", 3);

		Map<String, Map> datos = new HashMap<String, Map>();
		datos.put("heroe", datosHeroe);
		datos.put("amigo", datosAmigo);
		datos.put("ataque_misil", datosAtaqueMisil);

		return datos;
	}
}
